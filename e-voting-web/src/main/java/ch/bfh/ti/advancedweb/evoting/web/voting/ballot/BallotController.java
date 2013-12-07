package ch.bfh.ti.advancedweb.evoting.web.voting.ballot;

import ch.bfh.ti.advancedweb.evoting.VotingService;
import ch.bfh.ti.advancedweb.evoting.VotingStoppedException;
import ch.bfh.ti.advancedweb.evoting.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.evoting.web.utils.MessageUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Controls the current saved Ballots in the BallotModel
 */
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

    /**
     * Saves all current saved Ballots which are saved in the BallotModel
     *
     * @return index.xhtml view-id
     */
    public String saveAllBallots() {

        final String userId = currentUserModel.getUserId();

        for (MajorityBallot majorityBallot : ballotModel.getMajorityBallots()) {
            try {
                votingService.saveMajorityVote(userId, majorityBallot.getVotingId(), majorityBallot.getCandidates());
            } catch (VotingStoppedException e) {
                MessageUtils.addErrorMessage("voting.already.stopped.exception");
            }
        }

        for (ProportionalBallot proportionalBallot : ballotModel.getProportionalBallots()) {
            try {
                votingService.saveProportionalVote(userId, proportionalBallot.getVotingId(), proportionalBallot.getPartyListName(), proportionalBallot.getCandidates());
            } catch (VotingStoppedException e) {
                MessageUtils.addErrorMessage("voting.already.stopped.exception");
            }
        }

        for (ReferendumBallot referendumBallot : ballotModel.getReferendumBallots()) {
            try {
                votingService.saveReferendumVote(userId, referendumBallot.getVotingId(), referendumBallot.getAccept());
            } catch (VotingStoppedException e) {
                MessageUtils.addErrorMessage("voting.already.stopped.exception");
            }
        }

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        MessageUtils.addWarnMessage("saved.all.ballots");

        this.ballotModel.clearAll();

        return "index.xhtml?faces-redirect=true";
    }
}
