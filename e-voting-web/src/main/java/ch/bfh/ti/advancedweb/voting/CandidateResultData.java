package ch.bfh.ti.advancedweb.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

public class CandidateResultData implements Comparable<CandidateResultData> {

    private Candidate candidate;

    private int votes;

    public CandidateResultData(Candidate candidate, int votes) {
        this.candidate = candidate;
        this.votes = votes;
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
}
