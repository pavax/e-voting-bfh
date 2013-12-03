package ch.bfh.ti.advancedweb.web.voting;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Component
@Scope("request")
public class BallotController {

    private final BallotModel ballotModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    @Inject
    public BallotController(BallotModel ballotModel, VotingService votingService, CurrentUserModel currentUserModel) {
        this.ballotModel = ballotModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
    }


    public String saveAllBallots() {

        final String userId = currentUserModel.getUserId();

        for (MajorityBallot majorityBallot : ballotModel.getMajorityBallots()) {
            votingService.saveMajorityVote(userId, majorityBallot.getVotingId(), majorityBallot.getCandidates());
        }
        for (ProportionalBallot proportionalBallot : ballotModel.getProportionalBallots()) {
            votingService.saveProportionalVote(userId, proportionalBallot.getVotingId(), proportionalBallot.getCandidates());
        }

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        MessageUtils.addWarnMessage("saved.all.ballots");

        this.ballotModel.clearAll();

        return "index.xhtml?faces-redirect=true";

    }
}
