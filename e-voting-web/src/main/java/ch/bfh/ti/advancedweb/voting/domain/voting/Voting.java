package ch.bfh.ti.advancedweb.voting.domain.voting;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Voting {

    @Id
    private String votingId;

    private String title;

    @Enumerated(EnumType.STRING)
    private VotingType votingType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private boolean open = true;

    public Voting(String title, VotingType votingType) {
        this.votingId = UUID.randomUUID().toString();
        this.title = title;
        this.votingType = votingType;
        this.created = new Date();
    }

    protected Voting() {
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

    public Date getCreated() {
        return created;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
