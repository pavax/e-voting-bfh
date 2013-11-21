package ch.bfh.ti.advancedweb.web.votinglist;

import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProporzVoting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("view")
public class VotingListModel {

    private Map<MajorzVoting,Boolean> majorzVotingMap;
    private Map<ProporzVoting, Boolean> proporzVotingMap;

    public Map<MajorzVoting, Boolean> getMajorzVotingMap() {
        return majorzVotingMap;
    }

    public void setMajorzVotingMap(Map<MajorzVoting, Boolean> majorzVotingMap) {
        this.majorzVotingMap = majorzVotingMap;
    }


    public void setProporzVotingMap(Map<ProporzVoting, Boolean> proporzVotingMap) {
        this.proporzVotingMap = proporzVotingMap;
    }

    public Map<ProporzVoting, Boolean> getProporzVotingMap() {
        return proporzVotingMap;
    }
}
