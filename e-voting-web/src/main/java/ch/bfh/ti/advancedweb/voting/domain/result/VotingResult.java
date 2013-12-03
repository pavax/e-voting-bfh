package ch.bfh.ti.advancedweb.voting.domain.result;

import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public abstract class VotingResult {

    @Id
    private String votingResultId;

    @ManyToOne
    private Voting voting;

    @ManyToOne
    private User voter;

    public VotingResult(Voting voting, User voter) {
        this.votingResultId = UUID.randomUUID().toString();
        this.voting = voting;
        this.voter = voter;
    }

    protected VotingResult() {
        // FOR JPA
    }

    public User getVoter() {
        return voter;
    }

    public Voting getVoting() {
        return voting;
    }

    public String getVotingResultId() {
        return votingResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VotingResult)) return false;

        VotingResult that = (VotingResult) o;

        if (votingResultId != null ? !votingResultId.equals(that.votingResultId) : that.votingResultId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return votingResultId != null ? votingResultId.hashCode() : 0;
    }
}
