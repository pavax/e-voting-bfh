package ch.bfh.ti.advancedweb.web.voting.proportinal;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import ch.bfh.ti.advancedweb.web.voting.BallotModel;
import ch.bfh.ti.advancedweb.web.voting.CandidatePosition;
import ch.bfh.ti.advancedweb.web.voting.ProportionalBallot;
import ch.bfh.ti.advancedweb.web.votinglist.VotingState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (proportionalVotingModel.getVotingState().equals(VotingState.VOTED)) {
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingsFromUser(currentUserModel.getUserId(), proportionalVotingModel.getVoting());
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    selectCandidate(candidate);
                }
            } else if (proportionalVotingModel.getVotingState().equals(VotingState.SAVED)) {
                final ProportionalBallot proportionalBallots = ballotModel.findProportionalBallot(proportionalVotingModel.getVotingId());
                for (Candidate candidate : proportionalBallots.getCandidates()) {
                    selectCandidate(candidate);
                }
            }
        }
    }

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

    public void removedSelectedCandidate(int index) {
        proportionalVotingModel.getCandidatePositions().get(index).setCandidate(null);
    }

    public void selectAllFromParty(String partyName) {
        proportionalVotingModel.initCandidatePositions();
        final Map<String, List<Candidate>> partyCandidatesMap = proportionalVotingModel.getPartyCandidatesMap();
        final List<Candidate> candidateList = partyCandidatesMap.get(partyName);
        for (Candidate candidate : candidateList) {
            proportionalVotingModel.addCandidateToTheNextFreePosition(candidate);
        }
    }


    public String voteAction() {
        List<Candidate> selectedCanidates = new ArrayList<>();
        for (CandidatePosition candidatePosition : proportionalVotingModel.getCandidatePositions()) {
            if (candidatePosition.getCandidate() != null) {

                selectedCanidates.add(candidatePosition.getCandidate());
            }
        }

        ballotModel.addProportionalBallot(new ProportionalBallot(selectedCanidates, proportionalVotingModel.getVotingId()));
        //votingService.saveProportionalVote(currentUserModel.getUserId(), proportionalVotingModel.getVotingId(), selectedCanidates);
        proportionalVotingModel.clear();
        return "index.xhtml?faces-redirect=true";
    }

}
