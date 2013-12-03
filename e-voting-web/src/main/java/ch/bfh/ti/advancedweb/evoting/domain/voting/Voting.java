package ch.bfh.ti.advancedweb.evoting.domain.voting;

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
    private Date createDate;

    private boolean open = true;

    public Voting(String title, VotingType votingType) {
        this.votingId = UUID.randomUUID().toString();
        this.title = title;
        this.votingType = votingType;
        this.createDate = new Date();
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

    public Date getCreateDate() {
        return createDate;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voting)) return false;

        Voting voting = (Voting) o;

        if (votingId != null ? !votingId.equals(voting.votingId) : voting.votingId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return votingId != null ? votingId.hashCode() : 0;
    }
}
