package ch.bfh.ti.advancedweb.evoting.web.voting.proportinal;

import ch.bfh.ti.advancedweb.evoting.VotingService;
import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.evoting.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.evoting.web.voting.ballot.BallotModel;
import ch.bfh.ti.advancedweb.evoting.web.votinglist.VotingState;
import ch.bfh.ti.advancedweb.evoting.web.utils.MessageUtils;
import ch.bfh.ti.advancedweb.evoting.web.voting.CandidatePosition;
import ch.bfh.ti.advancedweb.evoting.web.voting.ballot.ProportionalBallot;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for the ProportionalVotingModel
 */
@Component
@Scope("request")
public class ProportionalVotingController {

    private final ProportionalVotingModel proportionalVotingModel;

    private final VotingService votingService;

    private final BallotModel ballotModel;

    private final CurrentUserModel currentUserModel;

    @Inject
    public ProportionalVotingController(ProportionalVotingModel proportionalVotingModel, VotingService votingService, BallotModel ballotModel, CurrentUserModel currentUserModel) {
        this.proportionalVotingModel = proportionalVotingModel;
        this.votingService = votingService;
        this.ballotModel = ballotModel;
        this.currentUserModel = currentUserModel;
    }

    /**
     * Prepares the ProportionalVotingModel if the User already saved a Vote (ether to the Sessions BallotModel or in the Database)
     * <p/>
     * Invoked on proportional.xhtml Page-Initialization
     */
    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (proportionalVotingModel.getVotingState().equals(VotingState.VOTED)) {
                proportionalVotingModel.initCandidatePositions();
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingResultForUserAndVotingId(currentUserModel.getUserId(), proportionalVotingModel.getVoting().getVotingId());
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    selectCandidate(candidate);
                }
                proportionalVotingModel.setPartyListName(votingResultForUser.getPartyListName());
            } else if (proportionalVotingModel.getVotingState().equals(VotingState.SAVED)) {
                proportionalVotingModel.initCandidatePositions();
                final ProportionalBallot proportionalBallot = ballotModel.findProportionalBallot(proportionalVotingModel.getVotingId());
                for (Candidate candidate : proportionalBallot.getCandidates()) {
                    selectCandidate(candidate);
                }
                proportionalVotingModel.setPartyListName(proportionalBallot.getPartyListName());
            }
        }
    }

    /**
     * Selects the given Candidate and sets it into the next free available position in the ProportionalVotingModel
     * <p/>
     * Also makes sure that a Candidate can max. be selected twice
     *
     * @param candidate the Candidate to select
     */
    public void selectCandidate(Candidate candidate) {
        if (proportionalVotingModel.isMaxPositionsFilled()) {
            MessageUtils.addWarnMessage("proporz.maxPositionsFilled");
            return;
        }

        if (proportionalVotingModel.candidateDisabled(candidate)) {
            MessageUtils.addWarnMessage("proporz.candidate.alreadyVoted");
            return;
        }

        proportionalVotingModel.addCandidateToTheNextFreePosition(candidate);
    }

    /**
     * Resets the CandidatePosition so that another Candidate can be selected
     *
     * @param index of the selected Candidate to remove
     */
    public void removedSelectedCandidate(int index) {
        proportionalVotingModel.getCandidatePositions().get(index).setCandidate(null);
    }

    /**
     * Clears all current selected Candidate from the ProportionalVotingModel
     * and selects all Candidate from the given party
     *
     * @param partyName selects all Candidate from the given party name
     */
    public void selectAllFromParty(String partyName) {
        proportionalVotingModel.initCandidatePositions();
        final Map<String, List<Candidate>> partyCandidatesMap = proportionalVotingModel.getPartyCandidatesMap();
        final List<Candidate> candidateList = partyCandidatesMap.get(partyName);
        for (Candidate candidate : candidateList) {
            proportionalVotingModel.addCandidateToTheNextFreePosition(candidate);
        }
        proportionalVotingModel.setPartyListName(partyName);
    }

    /**
     * Saves the selected Candidate from the ProportionalVotingModel to the BallotModel
     *
     * @return the index.xhtml view-id
     */
    public String saveProportionalVoting() {
        List<Candidate> selectedCanidates = new ArrayList<>();
        for (CandidatePosition candidatePosition : proportionalVotingModel.getCandidatePositions()) {
            if (candidatePosition.getCandidate() != null) {

                selectedCanidates.add(candidatePosition.getCandidate());
            }
        }

        ballotModel.addProportionalBallot(new ProportionalBallot(selectedCanidates, proportionalVotingModel.getVotingId(), proportionalVotingModel.getPartyListName()));
        proportionalVotingModel.clear();
        return "index.xhtml?faces-redirect=true";
    }

}
