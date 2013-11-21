package ch.bfh.ti.advancedweb.web.votinglist;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProporzVoting;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Map;

@Component
@Scope("request")
public class VotingListController {

    private final VotingService votingService;

    private final VotingListModel votingListModel;

    private final CurrentUserModel currentUserModel;

    @Inject
    public VotingListController(VotingService votingService, VotingListModel votingListModel, CurrentUserModel currentUserModel) {
        this.votingService = votingService;
        this.votingListModel = votingListModel;
        this.currentUserModel = currentUserModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final Map<MajorzVoting, Boolean> currentMajorzVotings = votingService.getCurrentMajorzVotings(currentUserModel.getUserId());
            votingListModel.setMajorzVotingMap(currentMajorzVotings);

            final Map<ProporzVoting, Boolean> currentProporzVotings = votingService.getCurrentProporzVotings(currentUserModel.getUserId());
            votingListModel.setProporzVotingMap(currentProporzVotings);
        }
    }
}
