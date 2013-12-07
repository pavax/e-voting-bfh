package ch.bfh.ti.advancedweb.evoting.web.admin.results;

import ch.bfh.ti.advancedweb.evoting.ReferendumResultData;
import ch.bfh.ti.advancedweb.evoting.VotingAdminService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Component
@Scope("request")
public class ReferendumVotingResultController {

    private final ReferendumVotingResultModel referendumVotingResultModel;

    private final VotingAdminService votingAdminService;

    @Inject
    public ReferendumVotingResultController(ReferendumVotingResultModel referendumVotingResultModel, VotingAdminService votingAdminService) {
        this.referendumVotingResultModel = referendumVotingResultModel;
        this.votingAdminService = votingAdminService;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final ReferendumResultData referendumResultData = votingAdminService.getReferendumResult(referendumVotingResultModel.getSelectedReferendumVoting().getVotingId());
            referendumVotingResultModel.clear();
            referendumVotingResultModel.setReferendumResultData(referendumResultData);
        }
    }
}
