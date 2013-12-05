package ch.bfh.ti.advancedweb.evoting.web.voting.ballot;

public class ReferendumBallot {

    private String votingId;

    private Boolean accept;

    public ReferendumBallot(Boolean accept, String votingId) {
        this.accept = accept;
        this.votingId = votingId;
    }

    public Boolean getAccept() {
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
