package ch.bfh.ti.advancedweb.evoting.domain.result;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class CandidateVotingResult extends VotingResult {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Candidate> votedCandidates = new ArrayList<>();

    public CandidateVotingResult(Voting voting, List<Candidate> votedCandidates, User voter) {
        super(voting, voter);
        this.votedCandidates.addAll(votedCandidates);
    }

    protected CandidateVotingResult() {
        // FOR JPA
        super();
    }

    public List<Candidate> getVotedCandidates() {
        return Collections.unmodifiableList(this.votedCandidates);
    }

}
