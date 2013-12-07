package ch.bfh.ti.advancedweb.evoting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

public class CandidateResultData implements Comparable<CandidateResultData> {

    private Candidate candidate;

    private int votes;

    private boolean elected;

    public CandidateResultData(Candidate candidate, int votes, boolean elected) {
        this.candidate = candidate;
        this.votes = votes;
        this.elected = elected;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public int getVotes() {
        return votes;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "CandidateResult{" +
                "candidate=" + candidate +
                ", votes=" + votes +
                '}';
    }

    @Override
    public int compareTo(CandidateResultData o) {
        return Integer.compare(o.votes, votes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateResultData)) return false;

        CandidateResultData that = (CandidateResultData) o;

        if (candidate != null ? !candidate.equals(that.candidate) : that.candidate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return candidate != null ? candidate.hashCode() : 0;
    }

    public boolean isElected() {
        return elected;
    }
}
