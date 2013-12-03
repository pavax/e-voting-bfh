package ch.bfh.ti.advancedweb.evoting.web.votinglist;

import ch.bfh.ti.advancedweb.evoting.VotingService;
import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ReferendumVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;
import ch.bfh.ti.advancedweb.evoting.web.voting.BallotModel;
import ch.bfh.ti.advancedweb.evoting.web.CurrentUserModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Scope("request")
public class VotingListController {

    private final VotingService votingService;

    private final VotingListModel votingListModel;

    private final CurrentUserModel currentUserModel;

    private final BallotModel ballotModel;

    @Inject
    public VotingListController(VotingService votingService, VotingListModel votingListModel, CurrentUserModel currentUserModel, BallotModel ballotModel) {
        this.votingService = votingService;
        this.votingListModel = votingListModel;
        this.currentUserModel = currentUserModel;
        this.ballotModel = ballotModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            final Map<MajorityVoting, Boolean> currentMajorityVotings = votingService.getCurrentMajorityVotings(currentUserModel.getUserId());
            votingListModel.setMajorityVotingMap(initVotingStateMap(currentMajorityVotings));

            final Map<ProportionalVoting, Boolean> currentProportionalVotings = votingService.getCurrentProportionalVotings(currentUserModel.getUserId());
            votingListModel.setProportionalVotingMap(initVotingStateMap(currentProportionalVotings));

            final Map<ReferendumVoting, Boolean> currentReferendumVotings = votingService.getCurrentReferendumVotings(currentUserModel.getUserId());
            votingListModel.setReferendumVotingMap(initVotingStateMap(currentReferendumVotings));
        }
    }

    private <T extends Voting> Map<T, VotingState> initVotingStateMap(Map<T, Boolean> votings) {
        final Map<T, VotingState> votingStateMap = new LinkedHashMap<>();
        for (T voting : votings.keySet()) {
            if (!voting.isOpen()) {
                votingStateMap.put(voting, VotingState.STOPPED);
            } else if (ballotModel.contains(voting.getVotingId())) {
                votingStateMap.put(voting, VotingState.SAVED);
            } else {
                if (votings.get(voting)) {
                    votingStateMap.put(voting, VotingState.VOTED);
                } else {
                    votingStateMap.put(voting, VotingState.NEW);
                }
            }
        }
        return votingStateMap;
    }
}
