package ch.bfh.ti.advancedweb.web.admin.results;

import ch.bfh.ti.advancedweb.voting.ReferendumResult;
import ch.bfh.ti.advancedweb.voting.VotingAdminService;
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
            final ReferendumResult referendumResult = votingAdminService.getReferendumResult(referendumVotingResultModel.getSelectedVoting().getVotingId());
            referendumVotingResultModel.clear();
            referendumVotingResultModel.setReferendumResult(referendumResult);
        }
    }
}
