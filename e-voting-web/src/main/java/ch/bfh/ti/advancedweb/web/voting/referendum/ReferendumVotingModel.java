package ch.bfh.ti.advancedweb.web.voting.referendum;

import ch.bfh.ti.advancedweb.voting.domain.voting.ReferendumVoting;
import ch.bfh.ti.advancedweb.web.votinglist.VotingState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ReferendumVotingModel {

    private ReferendumVoting selectedQuestionVoting;

    private VotingState votingState;

    private boolean accept;

    public void clear(){
        this.selectedQuestionVoting = null;
    }

    public ReferendumVoting getSelectedQuestionVoting() {
        return selectedQuestionVoting;
    }

    public void setSelectedQuestionVoting(ReferendumVoting selectedQuestionVoting) {
        this.selectedQuestionVoting = selectedQuestionVoting;
    }

    public VotingState getVotingState() {
        return votingState;
    }

    public void setVotingState(VotingState votingState) {
        this.votingState = votingState;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean getAlreadyVoted() {
        return votingState.equals(VotingState.VOTED);
    }
}
