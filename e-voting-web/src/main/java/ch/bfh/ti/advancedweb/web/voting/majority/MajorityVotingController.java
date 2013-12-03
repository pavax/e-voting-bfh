package ch.bfh.ti.advancedweb.web.voting.majority;


import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import ch.bfh.ti.advancedweb.web.voting.BallotModel;
import ch.bfh.ti.advancedweb.web.voting.CandidatePosition;
import ch.bfh.ti.advancedweb.web.voting.MajorityBallot;
import ch.bfh.ti.advancedweb.web.votinglist.VotingState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("request")
public class MajorityVotingController {

    private final MajorityVotingModel majorzModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    private final BallotModel ballotModel;

    @Inject
    public MajorityVotingController(MajorityVotingModel majorzModel, VotingService votingService, CurrentUserModel currentUserModel, BallotModel ballotModel) {
        this.majorzModel = majorzModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
        this.ballotModel = ballotModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (majorzModel.getVotingState().equals(VotingState.VOTED)) {
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingsFromUser(currentUserModel.getUserId(), majorzModel.getVoting());
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    this.selectCandidate(candidate);
                }
            } else if (majorzModel.getVotingState().equals(VotingState.SAVED)) {
                final MajorityBallot majorityBallot = ballotModel.findMajorityBallot(majorzModel.getVotingId());
                for (Candidate candidate : majorityBallot.getCandidates()) {
                    selectCandidate(candidate);
                }
            }
        }
    }

    public void selectCandidate(Candidate candidate) {

        if (majorzModel.isMaxPositionsFilled()) {
            MessageUtils.addWarnMessage("majorz.maxPositionsFilled");
            return;
        }

        if (majorzModel.candidateDisabled(candidate)) {
            MessageUtils.addWarnMessage("majorz.candidate.alreadyVoted");
            return;
        }

        majorzModel.addCandidateToTheNextFreePosition(candidate);
    }

    public void removedSelectedCandidate(int index) {
        majorzModel.getCandidatePositions().get(index).setCandidate(null);
    }

    public String voteAction() {
        Set<Candidate> selectedCanidates = new HashSet<>();
        for (CandidatePosition candidatePosition : majorzModel.getCandidatePositions()) {
            if (candidatePosition.getCandidate() != null) {
                selectedCanidates.add(candidatePosition.getCandidate());
            }
        }
        ballotModel.addMajorityBallot(new MajorityBallot(selectedCanidates, majorzModel.getVotingId()));
        //votingService.saveMajorityVote(currentUserModel.getUserId(), majorzModel.getVotingId(), selectedCanidates);
        majorzModel.clear();
        return "index.xhtml?faces-redirect=true";
    }
}
