package ch.bfh.ti.advancedweb.evoting.web.voting.majority;


import ch.bfh.ti.advancedweb.evoting.VotingService;
import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.evoting.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.evoting.web.utils.MessageUtils;
import ch.bfh.ti.advancedweb.evoting.web.voting.CandidatePosition;
import ch.bfh.ti.advancedweb.evoting.web.voting.ballot.BallotModel;
import ch.bfh.ti.advancedweb.evoting.web.voting.ballot.MajorityBallot;
import ch.bfh.ti.advancedweb.evoting.web.votinglist.VotingState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller for the MajorityVotingModel
 */
@Component
@Scope("request")
public class MajorityVotingController {

    private final MajorityVotingModel majorityVotingModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    private final BallotModel ballotModel;

    @Inject
    public MajorityVotingController(MajorityVotingModel majorityVotingModel, VotingService votingService, CurrentUserModel currentUserModel, BallotModel ballotModel) {
        this.majorityVotingModel = majorityVotingModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
        this.ballotModel = ballotModel;
    }

    /**
     * Prepares the MajorityVotingModel if the User already saved a Vote (ether to the Sessions BallotModel or in the Database)
     * <p/>
     * Invoked on majority.xhtml Page-Initialization
     */
    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            Set<Candidate> selectedCandidates = null;
            if (majorityVotingModel.getVotingState().equals(VotingState.VOTED)) {
                // Load the saved Candidates from the Databases
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingResultForUserAndVotingId(currentUserModel.getUserId(), majorityVotingModel.getVoting().getVotingId());
                selectedCandidates = new HashSet<>(votingResultForUser.getVotedCandidates());
            } else if (majorityVotingModel.getVotingState().equals(VotingState.SAVED)) {
                // Load the saved Candidates from the Ballot Session
                final MajorityBallot majorityBallot = ballotModel.findMajorityBallot(majorityVotingModel.getVotingId());
                selectedCandidates = majorityBallot.getCandidates();
            }
            if (selectedCandidates != null) {
                for (Candidate candidate : selectedCandidates) {
                    selectCandidate(candidate);
                }
            }
        }
    }

    /**
     * Selects the given Candidate and sets it into the next free available position in the MajorityVotingModel
     *
     * @param candidate the Candidate to select
     */
    public void selectCandidate(Candidate candidate) {

        if (majorityVotingModel.isMaxPositionsFilled()) {
            MessageUtils.addWarnMessage("majorz.maxPositionsFilled");
            return;
        }

        if (majorityVotingModel.candidateDisabled(candidate)) {
            MessageUtils.addWarnMessage("majorz.candidate.alreadyVoted");
            return;
        }

        majorityVotingModel.addCandidateToTheNextFreePosition(candidate);
    }

    /**
     * Resets the CandidatePosition so that another Candidate can be selected
     *
     * @param index of the selected Candidate to remove
     */
    public void removedSelectedCandidate(int index) {
        majorityVotingModel.getCandidatePositions().get(index).setCandidate(null);
    }

    /**
     * Saves the selected Candidate from the MajorityVotingModel to the BallotModel
     *
     * @return the index.xhtml view-id
     */
    public String saveMajorityVoting() {
        Set<Candidate> selectedCanidates = new HashSet<>();
        for (CandidatePosition candidatePosition : majorityVotingModel.getCandidatePositions()) {
            if (candidatePosition.getCandidate() != null) {
                selectedCanidates.add(candidatePosition.getCandidate());
            }
        }
        ballotModel.addMajorityBallot(new MajorityBallot(selectedCanidates, majorityVotingModel.getVotingId()));
        majorityVotingModel.clear();
        return "index.xhtml?faces-redirect=true";
    }
}
