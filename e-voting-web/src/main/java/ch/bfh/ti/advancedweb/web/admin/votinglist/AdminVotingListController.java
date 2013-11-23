package ch.bfh.ti.advancedweb.web.admin.votinglist;

import ch.bfh.ti.advancedweb.voting.VotingAdminService;
import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@Component
@Scope("request")
public class AdminVotingListController {

    private final VotingService votingService;

    private final VotingAdminService votingAdminService;

    private final AdminVotingListModel adminVotingListModel;

    @Inject
    public AdminVotingListController(VotingService votingService, VotingAdminService votingAdminService, AdminVotingListModel adminVotingListModel) {
        this.votingService = votingService;
        this.votingAdminService = votingAdminService;
        this.adminVotingListModel = adminVotingListModel;
    }


    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final List<Voting> currentVotings = votingAdminService.getCurrentVotings();
            adminVotingListModel.setVotingList(currentVotings);
        }
    }
}
