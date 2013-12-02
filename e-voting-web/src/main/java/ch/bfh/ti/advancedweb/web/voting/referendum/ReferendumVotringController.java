package ch.bfh.ti.advancedweb.web.voting.referendum;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.result.ReferendumVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
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

    @Inject
    public ReferendumVotringController(ReferendumVotingModel referendumVotingModel, VotingService votingService, CurrentUserModel currentUserModel) {
        this.referendumVotingModel = referendumVotingModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
    }

    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (referendumVotingModel.isAlreadyVoted()) {
                final ReferendumVotingResult votingResultForUser = (ReferendumVotingResult) votingService.getVotingsFromUser(currentUserModel.getUserId(), referendumVotingModel.getSelectedQuestionVoting());
                referendumVotingModel.setAccept(votingResultForUser.isVote());
            }
        }
    }

}
