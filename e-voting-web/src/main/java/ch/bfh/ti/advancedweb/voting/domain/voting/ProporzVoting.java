package ch.bfh.ti.advancedweb.voting.domain.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProporzVoting extends AbstractCandidateVoting {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROPORZ_VOTING", referencedColumnName = "votingId")
    private Set<Candidate> porporzCandidates = new HashSet<>();

    public ProporzVoting(String title, int openPositions, Set<Candidate> porporzCandidates) {
        super(title, VotingType.PROPORTZ, openPositions);
        this.porporzCandidates.addAll(porporzCandidates);
    }

    protected ProporzVoting() {
        // FOR JPA
    }


    public Set<Candidate> getPorporzCandidates() {
        return porporzCandidates;
    }
}
