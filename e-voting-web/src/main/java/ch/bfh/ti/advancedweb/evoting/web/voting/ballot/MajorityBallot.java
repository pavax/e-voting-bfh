package ch.bfh.ti.advancedweb.evoting.web.voting.ballot;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

import java.util.Set;

public class MajorityBallot {

    private String votingId;

    private Set<Candidate> candidates;

    public MajorityBallot(Set<Candidate> candidates, String votingId) {
        this.candidates = candidates;
        this.votingId = votingId;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getVotingId() {
        return votingId;
    }

    public void setVotingId(String votingId) {
        this.votingId = votingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MajorityBallot)) return false;

        MajorityBallot that = (MajorityBallot) o;

        if (votingId != null ? !votingId.equals(that.votingId) : that.votingId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return votingId != null ? votingId.hashCode() : 0;
    }
}
