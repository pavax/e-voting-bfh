package ch.bfh.ti.advancedweb.web.admin.results;

import ch.bfh.ti.advancedweb.voting.CandidateResult;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("session")
public class CandidateResultModel {

    private Voting selectedCandidateVoting;

    private List<CandidateResult> candidateResults;

    private PieChartModel pieModel;

    private CartesianChartModel chartModel;

    private CandidateResult selectedCandidate;

    public void clear() {
        this.candidateResults = null;
        this.pieModel = null;
        this.chartModel = null;
        this.selectedCandidate = null;
    }

    public Voting getSelectedCandidateVoting() {
        return selectedCandidateVoting;
    }

    public void setSelectedCandidateVoting(Voting selectedCandidateVoting) {
        this.selectedCandidateVoting = selectedCandidateVoting;
    }

    public void setCandidateResults(List<CandidateResult> candidateResults) {
        this.candidateResults = candidateResults;
        initCharts(candidateResults);
    }

    private void initCharts(List<CandidateResult> candidateResults) {
        this.pieModel = new PieChartModel();
        this.chartModel = new CartesianChartModel();
        final ChartSeries chartSeries = new ChartSeries();
        this.chartModel.addSeries(chartSeries);
        for (CandidateResult candidateResult : candidateResults) {
            final String category = candidateResult.getCandidate().getFirstName() + " " + candidateResult.getCandidate().getLastName() + " (" + candidateResult.getCandidate().getPartyName() + ")";
            this.pieModel.set(category, candidateResult.getVotes());
            chartSeries.set(category, candidateResult.getVotes());
        }
    }

    public List<CandidateResult> getCandidateResults() {
        return candidateResults;
    }


    public PieChartModel getPieModel() {
        return pieModel;
    }

    public CartesianChartModel getChartModel() {
        return chartModel;
    }

    public void setSelectedCandidate(CandidateResult selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public CandidateResult getSelectedCandidate() {
        return selectedCandidate;
    }
}
