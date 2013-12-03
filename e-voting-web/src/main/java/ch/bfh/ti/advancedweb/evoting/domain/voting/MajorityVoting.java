package ch.bfh.ti.advancedweb.evoting.domain.voting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MajorityVoting extends AbstractCandidateVoting {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MAYORZ_VOTING", referencedColumnName = "votingId")
    private Set<Candidate> majorityCandidates = new HashSet<>();

    public MajorityVoting(String title, int openPositions, Set<Candidate> majorityCandidates) {
        super(title, VotingType.MAJORITY, openPositions);
        this.majorityCandidates.addAll(majorityCandidates);
    }

    protected MajorityVoting() {
        // FOR JPA
    }

    public Set<Candidate> getMajorityCandidates() {
        return Collections.unmodifiableSet(this.majorityCandidates);
    }

}
