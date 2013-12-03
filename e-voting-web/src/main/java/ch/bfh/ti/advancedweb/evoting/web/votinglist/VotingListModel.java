package ch.bfh.ti.advancedweb.evoting.web.votinglist;

import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ReferendumVoting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("view")
public class VotingListModel {

    private Map<MajorityVoting, VotingState> majorityVotingMap;

    private Map<ProportionalVoting, VotingState> proportionalVotingMap;

    private Map<ReferendumVoting, VotingState> referendumVotingMap;

    public Map<MajorityVoting, VotingState> getMajorityVotingMap() {
        return majorityVotingMap;
    }

    public void setMajorityVotingMap(Map<MajorityVoting, VotingState> majorityVotingMap) {
        this.majorityVotingMap = majorityVotingMap;
    }


    public void setProportionalVotingMap(Map<ProportionalVoting, VotingState> proportionalVotingMap) {
        this.proportionalVotingMap = proportionalVotingMap;
    }

    public Map<ProportionalVoting, VotingState> getProportionalVotingMap() {
        return proportionalVotingMap;
    }

    public void setReferendumVotingMap(Map<ReferendumVoting, VotingState> questionVotingMap) {
        this.referendumVotingMap = questionVotingMap;
    }

    public Map<ReferendumVoting, VotingState> getReferendumVotingMap() {
        return referendumVotingMap;
    }
}
