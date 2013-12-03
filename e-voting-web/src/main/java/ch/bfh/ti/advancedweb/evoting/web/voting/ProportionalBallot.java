package ch.bfh.ti.advancedweb.evoting.web.voting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

import java.util.List;

public class ProportionalBallot {

    private String votingId;

    private List<Candidate> candidates;

    public ProportionalBallot(List<Candidate> candidates, String votingId) {
        this.candidates = candidates;
        this.votingId = votingId;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public String getVotingId() {
        return votingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProportionalBallot)) return false;

        ProportionalBallot that = (ProportionalBallot) o;

        if (votingId != null ? !votingId.equals(that.votingId) : that.votingId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return votingId != null ? votingId.hashCode() : 0;
    }
}
