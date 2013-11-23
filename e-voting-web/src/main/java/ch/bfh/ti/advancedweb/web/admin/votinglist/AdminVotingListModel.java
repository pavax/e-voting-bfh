package ch.bfh.ti.advancedweb.web.admin.votinglist;

import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("session")
public class AdminVotingListModel {

    private List<Voting> votingList;

    public List<Voting> getVotingList() {
        return votingList;
    }

    public void setVotingList(List<Voting> votingList) {
        this.votingList = votingList;
    }



}

