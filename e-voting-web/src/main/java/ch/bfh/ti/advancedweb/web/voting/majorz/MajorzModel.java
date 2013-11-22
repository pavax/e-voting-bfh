package ch.bfh.ti.advancedweb.web.voting.majorz;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVoting;
import ch.bfh.ti.advancedweb.web.voting.AbstractCandidateSelectionModel;
import ch.bfh.ti.advancedweb.web.voting.CandidatePosition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class MajorzModel extends AbstractCandidateSelectionModel<MajorzVoting> {

    private List<Candidate> possibleCandidates = new ArrayList<>();

    @Override
    public void clear() {
        super.clear();
        possibleCandidates.clear();
    }

    @Override
    public void initPossibleCandidates(MajorzVoting voting) {
        this.possibleCandidates.addAll(voting.getMajorzCandidates());
    }

    @Override
    public boolean candidateDisabled(Candidate candidate) {
        return this.getCandidatePositions().contains(new CandidatePosition(candidate));
    }

    public List<Candidate> getPossibleCandidates() {
        return possibleCandidates;
    }

}
