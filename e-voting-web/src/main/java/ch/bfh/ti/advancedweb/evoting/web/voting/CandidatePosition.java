package ch.bfh.ti.advancedweb.evoting.web.voting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

public class CandidatePosition {

    private Candidate candidate;

    public CandidatePosition(Candidate candidate) {
        this.candidate = candidate;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidatePosition)) return false;

        CandidatePosition that = (CandidatePosition) o;

        if (candidate != null ? !candidate.equals(that.candidate) : that.candidate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return candidate != null ? candidate.hashCode() : 0;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
