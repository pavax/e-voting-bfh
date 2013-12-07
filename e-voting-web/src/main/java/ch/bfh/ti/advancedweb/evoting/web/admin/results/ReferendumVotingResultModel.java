package ch.bfh.ti.advancedweb.evoting.web.admin.results;

import ch.bfh.ti.advancedweb.evoting.ReferendumResultData;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ReferendumVoting;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ReferendumVotingResultModel {

    private ReferendumVoting selectedReferendumVoting;

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
        this.pieModel.set("JA (Stimmen: " + referendumResultData.getAcceptCount() + ")", referendumResultData.getAcceptCount());
        this.pieModel.set("NEIN (Stimmen: " + referendumResultData.getRejectCount() + ")", referendumResultData.getRejectCount());
    }

    public ReferendumVoting getSelectedReferendumVoting() {
        return selectedReferendumVoting;
    }

    public void setSelectedReferendumVoting(ReferendumVoting selectedReferendumVoting) {
        this.selectedReferendumVoting = selectedReferendumVoting;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

}
