package ch.bfh.ti.advancedweb.web.voting.majorz;


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
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("request")
public class MajorzController {

    private final MajorzModel majorzModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    @Inject
    public MajorzController(MajorzModel majorzModel, VotingService votingService, CurrentUserModel currentUserModel) {
        this.majorzModel = majorzModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (majorzModel.isAlreadyVoted()) {
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingResultForUser(currentUserModel.getUserId(), majorzModel.getVoting());
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    this.selectCandidate(candidate);
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
        votingService.majorzVote(currentUserModel.getUserId(), majorzModel.getVotingId(), selectedCanidates);
        majorzModel.clear();
        return "index.xhtml?faces-redirect=true";
    }
}
