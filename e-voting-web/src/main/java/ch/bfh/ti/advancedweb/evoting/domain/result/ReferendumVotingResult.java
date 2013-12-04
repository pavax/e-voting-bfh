package ch.bfh.ti.advancedweb.evoting.domain.result;

import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;

import javax.persistence.Entity;

@Entity
public class ReferendumVotingResult extends VotingResult {

    private Boolean accept;

    public ReferendumVotingResult(Voting voting, Boolean accept, User voter) {
        super(voting, voter);
        this.accept = accept;
    }

    protected ReferendumVotingResult() {
        // FOR JPA
        super();
    }

    public Boolean isAccept() {
        return accept;
    }
}
