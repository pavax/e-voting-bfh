package ch.bfh.ti.advancedweb.web.voting.proportinal;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import ch.bfh.ti.advancedweb.web.voting.CandidatePosition;
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

    private final CurrentUserModel currentUserModel;

    @Inject
    public ProportionalVotingController(ProportionalVotingModel proportionalVotingModel, VotingService votingService, CurrentUserModel currentUserModel) {
        this.proportionalVotingModel = proportionalVotingModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
    }


    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (proportionalVotingModel.isAlreadyVoted()) {
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingsFromUser(currentUserModel.getUserId(), proportionalVotingModel.getVoting());
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
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
        votingService.saveProportionalVote(currentUserModel.getUserId(), proportionalVotingModel.getVotingId(), selectedCanidates);
        proportionalVotingModel.clear();
        return "index.xhtml?faces-redirect=true";
    }

}
