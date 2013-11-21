package ch.bfh.ti.advancedweb.voting.domain.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MajorzVoting extends Voting {

    private int openPositions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "MAYORZ_VOTING", referencedColumnName = "votingId")
    private Set<Candidate> majorzCandidates = new HashSet<>();

    public MajorzVoting(String title, int openPositions, Set<Candidate> majorzCandidates) {
        super(title, VotingType.MAYORZ);
        this.openPositions = openPositions;
        this.majorzCandidates.addAll(majorzCandidates);
    }

    protected MajorzVoting() {
        // FOR JPA
    }

    public Set<Candidate> getMajorzCandidates() {
        return majorzCandidates;
    }

    public int getOpenPositions() {
        return openPositions;
    }
}
