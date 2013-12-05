package ch.bfh.ti.advancedweb.evoting.web.admin.votinglist;

import ch.bfh.ti.advancedweb.evoting.VotingAdminService;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@Component
@Scope("request")
public class AdminVotingListController {

    private final VotingAdminService votingAdminService;

    private final AdminVotingListModel adminVotingListModel;

    @Inject
    public AdminVotingListController(VotingAdminService votingAdminService, AdminVotingListModel adminVotingListModel) {
        this.votingAdminService = votingAdminService;
        this.adminVotingListModel = adminVotingListModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final List<Voting> currentVotings = votingAdminService.getCurrentVotings();
            adminVotingListModel.setVotingList(currentVotings);
        }
    }

    public void stopAllVotings() {
        votingAdminService.stopAllCurrentVotings();
    }
}
