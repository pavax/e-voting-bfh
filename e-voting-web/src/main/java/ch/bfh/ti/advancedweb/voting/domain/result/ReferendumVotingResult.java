package ch.bfh.ti.advancedweb.voting.domain.result;

import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import javax.persistence.Entity;

@Entity
public class ReferendumVotingResult extends VotingResult {

    private boolean accept;

    public ReferendumVotingResult(Voting voting, boolean accept, User voter) {
        super(voting, voter);
        this.accept = accept;
    }

    protected ReferendumVotingResult() {
        // FOR JPA
        super();
    }

    public boolean isAccept() {
        return accept;
    }
}
