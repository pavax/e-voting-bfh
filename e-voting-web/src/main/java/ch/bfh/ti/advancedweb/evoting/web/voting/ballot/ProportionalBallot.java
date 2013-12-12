package ch.bfh.ti.advancedweb.evoting.web.voting.ballot;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

import java.util.List;

/**
 * Contains the Lust of selected Candidates of an User and the selected party to give the party votes
 */
public class ProportionalBallot {

    private String votingId;

    private List<Candidate> candidates;

    private String partyListName;

    public ProportionalBallot(List<Candidate> candidates, String votingId, String partyListName) {
        this.candidates = candidates;
        this.votingId = votingId;
        this.partyListName = partyListName;
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

    public void setPartyListName(String partyListName) {
        this.partyListName = partyListName;
    }

    public String getPartyListName() {
        return partyListName;
    }
}
