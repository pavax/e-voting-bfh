package ch.bfh.ti.advancedweb.web.admin.results;

import ch.bfh.ti.advancedweb.voting.CandidateResultData;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("session")
public class CandidateVotingResultModel {

    private Voting selectedCandidateVoting;

    private List<CandidateResultData> candidateResultDatas;

    private PieChartModel pieModel;

    private CartesianChartModel chartModel;

    private CandidateResultData selectedCandidate;

    public void clear() {
        this.candidateResultDatas = null;
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

    public void setCandidateResultDatas(List<CandidateResultData> candidateResultDatas) {
        this.candidateResultDatas = candidateResultDatas;
        initCharts(candidateResultDatas);
    }

    private void initCharts(List<CandidateResultData> candidateResultDatas) {
        this.pieModel = new PieChartModel();
        this.chartModel = new CartesianChartModel();
        final ChartSeries chartSeries = new ChartSeries();
        this.chartModel.addSeries(chartSeries);
        for (CandidateResultData candidateResultData : candidateResultDatas) {
            final String category = candidateResultData.getCandidate().getFirstName() + " " + candidateResultData.getCandidate().getLastName() + " (" + candidateResultData.getCandidate().getPartyName() + ")";
            this.pieModel.set(category, candidateResultData.getVotes());
            chartSeries.set(category, candidateResultData.getVotes());
        }
    }

    public List<CandidateResultData> getCandidateResultDatas() {
        return candidateResultDatas;
    }


    public PieChartModel getPieModel() {
        return pieModel;
    }

    public CartesianChartModel getChartModel() {
        return chartModel;
    }

    public void setSelectedCandidate(CandidateResultData selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public CandidateResultData getSelectedCandidate() {
        return selectedCandidate;
    }
}
