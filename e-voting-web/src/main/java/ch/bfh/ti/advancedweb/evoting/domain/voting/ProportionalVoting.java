package ch.bfh.ti.advancedweb.evoting.domain.voting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class ProportionalVoting extends AbstractCandidateVoting {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROPORTIONAL_VOTING", referencedColumnName = "votingId")
    private List<Candidate> proportionalCandidates = new ArrayList<>();

    public ProportionalVoting(String title, int openPositions, List<Candidate> proportionalCandidates) {
        super(title, VotingType.PROPORTIONAL, openPositions);
        this.proportionalCandidates.addAll(proportionalCandidates);
    }

    protected ProportionalVoting() {
        // FOR JPA
    }

    public List<Candidate> getProportionalCandidates() {
        return Collections.unmodifiableList(this.proportionalCandidates);
    }

    public Set<String> getAllPartyNames() {
        Set<String> allParties = new HashSet<>();
        for (Candidate proportionalCandidate : proportionalCandidates) {
            allParties.add(proportionalCandidate.getPartyName());
        }
        return allParties;
    }

    public List<Candidate> getCandidatesByParty(String partyName) {
        List<Candidate> candidatesListByParty = new ArrayList<>();
        for (Candidate proportionalCandidate : proportionalCandidates) {
            if (proportionalCandidate.getPartyName().equals(partyName)){
                candidatesListByParty.add(proportionalCandidate);
            }
        }
        return candidatesListByParty;
    }
}
