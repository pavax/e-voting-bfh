package ch.bfh.ti.advancedweb.evoting;

import java.util.Set;

public class PartyResultData implements Comparable<PartyResultData> {

    private String partyName;

    private Set<CandidateResultData> candidateResults;

    private int partyVotes;

    private final int partyPositionCount;

    public PartyResultData(String partyName, Set<CandidateResultData> candidateResults, int partyVotes, int partyPositionCount) {
        this.partyName = partyName;
        this.candidateResults = candidateResults;
        this.partyVotes = partyVotes;
        this.partyPositionCount = partyPositionCount;
    }

    public String getPartyName() {
        return partyName;
    }

    public Set<CandidateResultData> getCandidateResults() {
        return candidateResults;
    }

    public int getPartyVotes() {
        return partyVotes;
    }

    public int getPartyPositionCount() {
        return partyPositionCount;
    }

    @Override
    public int compareTo(PartyResultData o) {
        return Integer.compare(o.partyVotes, partyVotes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyResultData)) return false;

        PartyResultData that = (PartyResultData) o;

        if (partyName != null ? !partyName.equals(that.partyName) : that.partyName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return partyName != null ? partyName.hashCode() : 0;
    }
}
