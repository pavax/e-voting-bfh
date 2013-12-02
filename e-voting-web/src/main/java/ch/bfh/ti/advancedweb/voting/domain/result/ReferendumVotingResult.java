package ch.bfh.ti.advancedweb.voting.domain.result;

import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

public class ReferendumVotingResult extends VotingResult {

    private boolean vote;

    public ReferendumVotingResult(Voting voting, boolean vote, User voter) {
        super(voting, voter);
        this.vote = vote;
    }

    protected ReferendumVotingResult() {
        // FOR JPA
        super();
    }

    public boolean isVote() {
        return vote;
    }
}
