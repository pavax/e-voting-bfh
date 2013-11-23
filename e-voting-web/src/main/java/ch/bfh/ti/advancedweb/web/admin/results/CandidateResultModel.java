package ch.bfh.ti.advancedweb.web.admin.results;

import ch.bfh.ti.advancedweb.voting.CandidateResult;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("session")
public class CandidateResultModel {

    private Voting selectedCandidateVoting;

    private List<CandidateResult> candidateResults;

    public Voting getSelectedCandidateVoting() {
        return selectedCandidateVoting;
    }

    public void setSelectedCandidateVoting(Voting selectedCandidateVoting) {
        this.selectedCandidateVoting = selectedCandidateVoting;
    }

    public void setCandidateResults(List<CandidateResult> candidateResults) {
        this.candidateResults = candidateResults;
    }

    public List<CandidateResult> getCandidateResults() {
        return candidateResults;
    }
}
