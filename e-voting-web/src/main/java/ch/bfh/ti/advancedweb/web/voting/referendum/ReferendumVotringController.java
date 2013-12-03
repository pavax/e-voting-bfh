package ch.bfh.ti.advancedweb.web.voting.referendum;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.result.ReferendumVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.voting.BallotModel;
import ch.bfh.ti.advancedweb.web.voting.ReferendumBallot;
import ch.bfh.ti.advancedweb.web.votinglist.VotingState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;


@Component
@Scope("request")
public class ReferendumVotringController {

    private final ReferendumVotingModel referendumVotingModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    private final BallotModel ballotModel;

    @Inject
    public ReferendumVotringController(ReferendumVotingModel referendumVotingModel, VotingService votingService, CurrentUserModel currentUserModel, BallotModel ballotModel) {
        this.referendumVotingModel = referendumVotingModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
        this.ballotModel = ballotModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (referendumVotingModel.getVotingState().equals(VotingState.VOTED)) {
                final ReferendumVotingResult votingResultForUser = (ReferendumVotingResult) votingService.getVotingFromUser(currentUserModel.getUserId(), referendumVotingModel.getSelectedQuestionVoting());
                referendumVotingModel.setAccept(votingResultForUser.isAccept());
            } else if (referendumVotingModel.getVotingState().equals(VotingState.SAVED)) {
                final ReferendumBallot referendumBallot = ballotModel.findReferendumBallot(referendumVotingModel.getSelectedQuestionVoting().getVotingId());
                referendumVotingModel.setAccept(referendumBallot.getAccept());
            }
        }
    }

    public String voteAction() {
        ballotModel.addReferendumBallot(new ReferendumBallot(referendumVotingModel.isAccept(), referendumVotingModel.getSelectedQuestionVoting().getVotingId()));
        referendumVotingModel.clear();
        return "index.xhtml?faces-redirect=true";
    }

}
