package ch.bfh.ti.advancedweb.evoting.web.admin.results;


import ch.bfh.ti.advancedweb.evoting.CandidateResultData;
import ch.bfh.ti.advancedweb.evoting.PartyResultData;
import ch.bfh.ti.advancedweb.evoting.ProportionalVotingResultData;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ProportionalVotingResultModel {

    private ProportionalVoting selectedProportionalVoting;

    private ProportionalVotingResultData proportionalVotingResultData;

    private PieChartModel partyPieChartModel;

    private CandidateResultData selectedCandidate;


    public void clear() {
        this.proportionalVotingResultData = null;
        this.partyPieChartModel = null;

    }

    public void setProportionalVotingResultData(ProportionalVotingResultData proportionalVotingResultData) {
        this.proportionalVotingResultData = proportionalVotingResultData;
        initCharts();
    }

    private void initCharts() {
        //
        partyPieChartModel = new PieChartModel();
        for (PartyResultData partyResultData : proportionalVotingResultData.getPartyResultDatas()) {
            partyPieChartModel.set(partyResultData.getPartyName() + " (Stimmen: " + partyResultData.getPartyVotes() + " | Sitze: " + partyResultData.getPartyPositionCount() + ")", partyResultData.getPartyVotes());
        }

    }

    public ProportionalVotingResultData getProportionalVotingResultData() {
        return proportionalVotingResultData;
    }

    public ProportionalVoting getSelectedProportionalVoting() {
        return selectedProportionalVoting;
    }

    public void setSelectedProportionalVoting(ProportionalVoting selectedProportionalVoting) {
        this.selectedProportionalVoting = selectedProportionalVoting;
    }

    public CandidateResultData getSelectedCandidate() {
        return selectedCandidate;
    }

    public void setSelectedCandidate(CandidateResultData selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public PieChartModel getPartyPieChartModel() {
        return partyPieChartModel;
    }
}
