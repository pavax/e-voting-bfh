package ch.bfh.ti.advancedweb.web.votinglist;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ReferendumVoting;
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
            final Map<MajorityVoting, Boolean> currentMajorityVotings = votingService.getCurrentMajorityVotings(currentUserModel.getUserId());
            votingListModel.setMajorityVotingMap(currentMajorityVotings);

            final Map<ProportionalVoting, Boolean> currentProportionalVotings = votingService.getCurrentProportionalVotings(currentUserModel.getUserId());
            votingListModel.setProportionalVotingMap(currentProportionalVotings);

            final Map<ReferendumVoting, Boolean> currentReferendumVotings = votingService.getCurrentReferendumVotings(currentUserModel.getUserId());
            votingListModel.setReferendumVotingMap(currentReferendumVotings);
        }
    }
}
