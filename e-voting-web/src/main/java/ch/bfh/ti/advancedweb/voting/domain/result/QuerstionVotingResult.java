package ch.bfh.ti.advancedweb.voting.domain.result;

import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

public class QuerstionVotingResult extends VotingResult {

    private boolean vote;

    public QuerstionVotingResult(Voting voting, boolean vote, User voter) {
        super(voting, voter);
        this.vote = vote;
    }

    protected QuerstionVotingResult() {
        // FOR JPA
        super();
    }

    public boolean isVote() {
        return vote;
    }
}
