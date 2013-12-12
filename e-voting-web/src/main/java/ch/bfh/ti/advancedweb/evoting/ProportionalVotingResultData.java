package ch.bfh.ti.advancedweb.evoting;

import java.util.Set;

public class ProportionalVotingResultData {

    private final Set<PartyResultData> partyResultDatas;

    private final Integer countTotalPartyVotes;

    public ProportionalVotingResultData(Set<PartyResultData> partyResultDatas, Integer countTotalPartyVotes) {
        this.partyResultDatas = partyResultDatas;
        this.countTotalPartyVotes = countTotalPartyVotes;
    }

    public Set<PartyResultData> getPartyResultDatas() {
        return partyResultDatas;
    }

    public Integer getCountTotalPartyVotes() {
        return countTotalPartyVotes;
    }
}
