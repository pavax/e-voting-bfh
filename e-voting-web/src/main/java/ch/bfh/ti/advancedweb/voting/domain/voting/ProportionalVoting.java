package ch.bfh.ti.advancedweb.voting.domain.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProportionalVoting extends AbstractCandidateVoting {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROPORZ_VOTING", referencedColumnName = "votingId")
    private List<Candidate> proportionalCandidates = new ArrayList<>();

    public ProportionalVoting(String title, int openPositions, List<Candidate> proportionalCandidates) {
        super(title, VotingType.PROPORTIONAL, openPositions);
        this.proportionalCandidates.addAll(proportionalCandidates);
    }

    protected ProportionalVoting() {
        // FOR JPA
    }


    public List<Candidate> getProportionalCandidates() {
        return proportionalCandidates;
    }
}
