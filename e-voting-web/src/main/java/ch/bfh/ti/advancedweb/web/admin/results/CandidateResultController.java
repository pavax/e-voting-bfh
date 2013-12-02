package ch.bfh.ti.advancedweb.web.admin.results;


import ch.bfh.ti.advancedweb.voting.CandidateResult;
import ch.bfh.ti.advancedweb.voting.VotingAdminService;
import org.primefaces.event.ItemSelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

@Component
@Scope("request")
public class CandidateResultController {

    private final CandidateResultModel candidateResultModel;

    private final VotingAdminService votingAdminService;

    @Inject
    public CandidateResultController(CandidateResultModel adminModel, VotingAdminService votingAdminService) {
        this.candidateResultModel = adminModel;
        this.votingAdminService = votingAdminService;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final Set<CandidateResult> candidateResults = votingAdminService.getCandidateResults(candidateResultModel.getSelectedCandidateVoting().getVotingId());
            candidateResultModel.clear();
            candidateResultModel.setCandidateResults(new ArrayList<>(candidateResults));
        }
    }

    public void candidateSelectedEvent(ItemSelectEvent event) {
        final CandidateResult selectedCandidate = this.candidateResultModel.getCandidateResults().get(event.getItemIndex());
        this.candidateResultModel.setSelectedCandidate(selectedCandidate);
    }
}
