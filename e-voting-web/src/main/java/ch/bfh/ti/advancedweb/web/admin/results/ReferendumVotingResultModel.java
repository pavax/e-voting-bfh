package ch.bfh.ti.advancedweb.web.admin.results;

import ch.bfh.ti.advancedweb.voting.ReferendumResult;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ReferendumVotingResultModel {

    private Voting selectedVoting;

    private ReferendumResult referendumResult;

    private PieChartModel pieModel;


    public void clear() {
        this.pieModel = null;
        this.referendumResult = null;
    }

    public ReferendumResult getReferendumResult() {
        return referendumResult;
    }

    public void setReferendumResult(ReferendumResult referendumResult) {
        this.referendumResult = referendumResult;
        this.pieModel = new PieChartModel();
        this.pieModel.set("JA", referendumResult.getAcceptCount());
        this.pieModel.set("NEIN", referendumResult.getRejectCount());
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
