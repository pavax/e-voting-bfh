package ch.bfh.ti.advancedweb.web.voting.referendum;

import ch.bfh.ti.advancedweb.voting.domain.voting.ReferendumVoting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ReferendumVotingModel {

    private ReferendumVoting selectedQuestionVoting;

    private boolean alreadyVoted;

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

    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }

    public void setAlreadyVoted(boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
