package ch.bfh.ti.advancedweb.web.majorz;


import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Component
@Scope("request")
public class MajorzController {

    private final MajorzModel majorzModel;

    private boolean singleSelectionForCandidate = false;

    private boolean customCandidateChoose = false;

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
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingResultForUser(currentUserModel.getUserId(), majorzModel.getSelectedMajorzVoting());
                majorzModel.getSelectedCandidates().clear();
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    selectCandidate(candidate);
                }
            }
        }
    }

    public MajorzModel getMajorzModel() {
        return majorzModel;
    }

    public void selectCandidate(Candidate candidate) {
        final List<Candidate> selectedCandidates = majorzModel.getSelectedCandidates();
        final List<Candidate> possibleCandidates = majorzModel.getPossibleCandidates();

        if (majorzModel.isMaxPositionsFilled()) {
            MessageUtils.addWarnMessage("majorz.maxPositionsFilled");
            return;
        }

        if (singleSelectionForCandidate) {
            if (selectedCandidates.contains(candidate)) {
                MessageUtils.addWarnMessage("majorz.candidate.alreadyVoted");
                return;
            }
        } else {
            final int i = Collections.frequency(selectedCandidates, candidate);
            if (i == 2) {
                MessageUtils.addWarnMessage("majorz.candidate.alreadyVoted");
                return;
            }
        }
        selectedCandidates.add(candidate);

        if (singleSelectionForCandidate) {
            possibleCandidates.remove(candidate);
        } else {
            final int i = Collections.frequency(selectedCandidates, candidate);
            if (i == 2) {
                possibleCandidates.remove(candidate);
            }
        }
    }

    public void removedSelectedCandidate(Candidate candidate) {
        majorzModel.getSelectedCandidates().remove(candidate);
    }

    public String voteAction() {
        votingService.vote(currentUserModel.getUserId(), majorzModel.getSelectedMajorzVoting(), majorzModel.getSelectedCandidates());
        majorzModel.clear();
        return "index.xhtml?faces-redirect=true";
    }
}
