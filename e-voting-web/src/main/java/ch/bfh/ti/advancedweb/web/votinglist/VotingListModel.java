package ch.bfh.ti.advancedweb.web.votinglist;

import ch.bfh.ti.advancedweb.voting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ReferendumVoting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("view")
public class VotingListModel {

    private Map<MajorityVoting, Boolean> majorityVotingMap;

    private Map<ProportionalVoting, Boolean> proportionalVotingMap;

    private Map<ReferendumVoting, Boolean> referendumVotingMap;

    public Map<MajorityVoting, Boolean> getMajorityVotingMap() {
        return majorityVotingMap;
    }

    public void setMajorityVotingMap(Map<MajorityVoting, Boolean> majorityVotingMap) {
        this.majorityVotingMap = majorityVotingMap;
    }


    public void setProportionalVotingMap(Map<ProportionalVoting, Boolean> proportionalVotingMap) {
        this.proportionalVotingMap = proportionalVotingMap;
    }

    public Map<ProportionalVoting, Boolean> getProportionalVotingMap() {
        return proportionalVotingMap;
    }

    public void setReferendumVotingMap(Map<ReferendumVoting, Boolean> questionVotingMap) {
        this.referendumVotingMap = questionVotingMap;
    }

    public Map<ReferendumVoting, Boolean> getReferendumVotingMap() {
        return referendumVotingMap;
    }
}
