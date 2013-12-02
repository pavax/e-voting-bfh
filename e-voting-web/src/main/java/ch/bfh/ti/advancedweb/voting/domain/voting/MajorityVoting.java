package ch.bfh.ti.advancedweb.voting.domain.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MajorityVoting extends AbstractCandidateVoting {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MAYORZ_VOTING", referencedColumnName = "votingId")
    private Set<Candidate> majorzCandidates = new HashSet<>();

    public MajorityVoting(String title, int openPositions, Set<Candidate> majorzCandidates) {
        super(title, VotingType.MAYORZ, openPositions);
        this.majorzCandidates.addAll(majorzCandidates);
    }

    protected MajorityVoting() {
        // FOR JPA
    }

    public Set<Candidate> getMajorzCandidates() {
        return majorzCandidates;
    }

}
