package ch.bfh.ti.advancedweb.evoting.domain.result;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.VotingType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class CandidateVotingResult extends VotingResult {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Candidate> votedCandidates = new ArrayList<>();

    public CandidateVotingResult(Voting voting, List<Candidate> votedCandidates, User voter) {
        super(voting, voter);
        validateProportionalVotingType(voting);
        checkVotedCandidateFrequency(votedCandidates);
        this.votedCandidates.addAll(votedCandidates);
    }

    public CandidateVotingResult(Voting voting, Set<Candidate> votedCandidates, User voter) {
        super(voting, voter);
        validateMajorityVotingType(voting);
        this.votedCandidates.addAll(votedCandidates);
    }

    private void validateProportionalVotingType(Voting voting) {
        if (!voting.getVotingType().equals(VotingType.PROPORTIONAL)) {
            throw new IllegalArgumentException("Only Voting of Type PROPORTIONAL are allowed in combination with a List");
        }
    }

    private void validateMajorityVotingType(Voting voting) {
        if (!voting.getVotingType().equals(VotingType.MAJORITY)) {
            throw new IllegalArgumentException("Only Voting of Type MAJORITY are allowed in combination with a Set");
        }
    }

    private void checkVotedCandidateFrequency(List<Candidate> votedCandidates) {
        for (Candidate votedCandidate : votedCandidates) {
            if (Collections.frequency(votedCandidates, votedCandidate) > 2) {
                throw new IllegalArgumentException("A Candidate can max. occur 2 times within the List");
            }
        }
    }

    protected CandidateVotingResult() {
        // FOR JPA
        super();
    }

    public List<Candidate> getVotedCandidates() {
        return Collections.unmodifiableList(this.votedCandidates);
    }

}
