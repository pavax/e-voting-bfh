package ch.bfh.ti.advancedweb.voting.domain.voting;

import javax.persistence.*;
import java.util.UUID;

@Entity
public abstract class Voting {

    @Id
    private String votingId;

    private String title;

    @Enumerated(EnumType.STRING)
    private VotingType votingType;

    public Voting(String title, VotingType votingType) {
        this.votingId = UUID.randomUUID().toString();
        this.title = title;
        this.votingType = votingType;
    }

    protected Voting(){
        // FOR JPA
    }

    public String getTitle() {
        return title;
    }

    public String getVotingId() {
        return votingId;
    }

    public VotingType getVotingType() {
        return votingType;
    }
}
