package ch.bfh.ti.advancedweb.evoting.domain.voting;

import javax.persistence.Entity;

@Entity
public abstract class AbstractCandidateVoting extends Voting {

    private int openPositions;

    public AbstractCandidateVoting(String title, VotingType votingType, int openPositions) {
        super(title, votingType);
        this.openPositions = openPositions;
    }

    protected AbstractCandidateVoting() {
        // FOR JPA
    }

    public int getOpenPositions() {
        return openPositions;
    }
}
