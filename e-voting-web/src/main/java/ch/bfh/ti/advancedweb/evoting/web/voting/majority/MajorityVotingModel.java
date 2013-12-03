package ch.bfh.ti.advancedweb.evoting.web.voting.majority;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.evoting.web.voting.AbstractCandidateSelectionModel;
import ch.bfh.ti.advancedweb.evoting.web.voting.CandidatePosition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class MajorityVotingModel extends AbstractCandidateSelectionModel<MajorityVoting> {

    private List<Candidate> possibleCandidates = new ArrayList<>();

    @Override
    public void clear() {
        super.clear();
        possibleCandidates.clear();
    }

    @Override
    public void initPossibleCandidates(MajorityVoting voting) {
        this.possibleCandidates.addAll(voting.getMajorityCandidates());
    }

    @Override
    public boolean candidateDisabled(Candidate candidate) {
        return this.getCandidatePositions().contains(new CandidatePosition(candidate));
    }

    public List<Candidate> getPossibleCandidates() {
        return possibleCandidates;
    }

}
