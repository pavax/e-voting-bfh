package ch.bfh.ti.advancedweb.evoting.web.admin.results;

import ch.bfh.ti.advancedweb.evoting.ReferendumResultData;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ReferendumVotingResultModel {

    private Voting selectedVoting;

    private ReferendumResultData referendumResultData;

    private PieChartModel pieModel;


    public void clear() {
        this.pieModel = null;
        this.referendumResultData = null;
    }

    public ReferendumResultData getReferendumResultData() {
        return referendumResultData;
    }

    public void setReferendumResultData(ReferendumResultData referendumResultData) {
        this.referendumResultData = referendumResultData;
        this.pieModel = new PieChartModel();
        this.pieModel.set("JA", referendumResultData.getAcceptCount());
        this.pieModel.set("NEIN", referendumResultData.getRejectCount());
    }

    public Voting getSelectedVoting() {
        return selectedVoting;
    }

    public void setSelectedVoting(Voting selectedVoting) {
        this.selectedVoting = selectedVoting;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

}
