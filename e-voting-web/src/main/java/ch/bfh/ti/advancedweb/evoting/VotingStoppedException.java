package ch.bfh.ti.advancedweb.evoting;

public class VotingStoppedException extends Exception {
    private final String votingId;

    public VotingStoppedException(String votingId) {
        this.votingId = votingId;
    }

    public String getVotingId() {
        return votingId;
    }
}
