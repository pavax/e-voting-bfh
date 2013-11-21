package ch.bfh.ti.advancedweb.voting.domain.result;

import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class VotingResult {

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
}
