package ch.bfh.ti.advancedweb.evoting.web.admin.results;

import ch.bfh.ti.advancedweb.evoting.CandidateResultData;
import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("session")
public class MajorityVotingResultModel {

    private MajorityVoting selectedMajorityVoting;

    private List<CandidateResultData> candidateResultDatas;

    private PieChartModel pieModel;

    private CandidateResultData selectedCandidate;

    public void clear() {
        this.candidateResultDatas = null;
        this.pieModel = null;
        this.selectedCandidate = null;
    }

    public MajorityVoting getSelectedMajorityVoting() {
        return selectedMajorityVoting;
    }

    public void setSelectedMajorityVoting(MajorityVoting selectedMajorityVoting) {
        this.selectedMajorityVoting = selectedMajorityVoting;
    }

    public void setCandidateResultDatas(List<CandidateResultData> candidateResultDatas) {
        this.candidateResultDatas = candidateResultDatas;
        initCharts(candidateResultDatas);
    }

    private void initCharts(List<CandidateResultData> candidateResultDatas) {
        this.pieModel = new PieChartModel();
        final ChartSeries chartSeries = new ChartSeries();
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

    public void setSelectedCandidate(CandidateResultData selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public CandidateResultData getSelectedCandidate() {
        return selectedCandidate;
    }
}
