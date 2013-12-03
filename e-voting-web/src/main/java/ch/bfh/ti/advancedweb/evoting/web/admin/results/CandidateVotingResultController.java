package ch.bfh.ti.advancedweb.evoting.web.admin.results;


import ch.bfh.ti.advancedweb.evoting.CandidateResultData;
import ch.bfh.ti.advancedweb.evoting.VotingAdminService;
import org.primefaces.event.ItemSelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

@Component
@Scope("request")
public class CandidateVotingResultController {

    private final CandidateVotingResultModel candidateVotingResultModel;

    private final VotingAdminService votingAdminService;

    @Inject
    public CandidateVotingResultController(CandidateVotingResultModel adminModel, VotingAdminService votingAdminService) {
        this.candidateVotingResultModel = adminModel;
        this.votingAdminService = votingAdminService;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final Set<CandidateResultData> candidateResultDatas = votingAdminService.getCandidateResults(candidateVotingResultModel.getSelectedCandidateVoting().getVotingId());
            candidateVotingResultModel.clear();
            candidateVotingResultModel.setCandidateResultDatas(new ArrayList<>(candidateResultDatas));
        }
    }

    public void candidateSelectedEvent(ItemSelectEvent event) {
        final CandidateResultData selectedCandidate = this.candidateVotingResultModel.getCandidateResultDatas().get(event.getItemIndex());
        this.candidateVotingResultModel.setSelectedCandidate(selectedCandidate);
    }
}
