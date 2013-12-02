package ch.bfh.ti.advancedweb.voting.domain.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProportionalVoting extends AbstractCandidateVoting {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROPORZ_VOTING", referencedColumnName = "votingId")
    private List<Candidate> porporzCandidates = new ArrayList<>();

    public ProportionalVoting(String title, int openPositions, List<Candidate> porporzCandidates) {
        super(title, VotingType.PROPORTZ, openPositions);
        this.porporzCandidates.addAll(porporzCandidates);
    }

    protected ProportionalVoting() {
        // FOR JPA
    }


    public List<Candidate> getPorporzCandidates() {
        return porporzCandidates;
    }
}
