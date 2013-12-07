package ch.bfh.ti.advancedweb.evoting.web.admin.results;

import ch.bfh.ti.advancedweb.evoting.ProportionalVotingResultData;
import ch.bfh.ti.advancedweb.evoting.VotingAdminService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Component
@Scope("request")
public class ProportionalVotingResultController {

    private final VotingAdminService votingAdminService;

    private final ProportionalVotingResultModel proportionalVotingResultModel;

    @Inject
    public ProportionalVotingResultController(VotingAdminService votingAdminService, ProportionalVotingResultModel proportionalVotingResultModel) {
        this.votingAdminService = votingAdminService;
        this.proportionalVotingResultModel = proportionalVotingResultModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final ProportionalVotingResultData proportionalVotingResultData = votingAdminService.getProportionalVotingResultData(proportionalVotingResultModel.getSelectedProportionalVoting().getVotingId());
            proportionalVotingResultModel.clear();
            proportionalVotingResultModel.setProportionalVotingResultData(proportionalVotingResultData);

        }
    }
}
