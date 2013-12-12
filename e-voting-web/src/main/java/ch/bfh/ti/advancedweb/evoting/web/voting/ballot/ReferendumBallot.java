package ch.bfh.ti.advancedweb.evoting.web.voting.ballot;

/**
 * Contains whether the User accepts or rejects a referendum
 */
public class ReferendumBallot {

    private String votingId;

    private boolean accept;

    public ReferendumBallot(boolean accept, String votingId) {
        this.accept = accept;
        this.votingId = votingId;
    }

    public boolean getAccept() {
        return accept;
    }

    public String getVotingId() {
        return votingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferendumBallot)) return false;

        ReferendumBallot that = (ReferendumBallot) o;

        if (votingId != null ? !votingId.equals(that.votingId) : that.votingId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return votingId != null ? votingId.hashCode() : 0;
    }
}
