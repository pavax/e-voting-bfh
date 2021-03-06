package ch.bfh.ti.advancedweb.evoting.web.voting.referendum;

import ch.bfh.ti.advancedweb.evoting.VotingService;
import ch.bfh.ti.advancedweb.evoting.domain.result.ReferendumVotingResult;
import ch.bfh.ti.advancedweb.evoting.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.evoting.web.voting.ballot.BallotModel;
import ch.bfh.ti.advancedweb.evoting.web.voting.ballot.ReferendumBallot;
import ch.bfh.ti.advancedweb.evoting.web.votinglist.VotingState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;


@Component
@Scope("request")
public class ReferendumVotingController {

    private final ReferendumVotingModel referendumVotingModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    private final BallotModel ballotModel;

    @Inject
    public ReferendumVotingController(ReferendumVotingModel referendumVotingModel, VotingService votingService, CurrentUserModel currentUserModel, BallotModel ballotModel) {
        this.referendumVotingModel = referendumVotingModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
        this.ballotModel = ballotModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (referendumVotingModel.getVotingState().equals(VotingState.VOTED)) {
                final ReferendumVotingResult votingResultForUser = (ReferendumVotingResult) votingService.getVotingResultForUserAndVotingId(currentUserModel.getUserId(), referendumVotingModel.getSelectedQuestionVoting().getVotingId());
                referendumVotingModel.setAccept(votingResultForUser.isAccept());
            } else if (referendumVotingModel.getVotingState().equals(VotingState.SAVED)) {
                final ReferendumBallot referendumBallot = ballotModel.findReferendumBallot(referendumVotingModel.getSelectedQuestionVoting().getVotingId());
                referendumVotingModel.setAccept(referendumBallot.getAccept());
            }
        }
    }

    public String saveReferendumVoting() {
        ballotModel.addReferendumBallot(new ReferendumBallot(referendumVotingModel.isAccept(), referendumVotingModel.getSelectedQuestionVoting().getVotingId()));
        referendumVotingModel.clear();
        return "index.xhtml?faces-redirect=true";
    }

}
