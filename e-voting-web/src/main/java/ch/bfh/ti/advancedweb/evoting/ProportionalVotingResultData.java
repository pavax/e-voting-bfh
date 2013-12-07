package ch.bfh.ti.advancedweb.evoting;

import java.util.Set;

public class ProportionalVotingResultData {

    private final Set<PartyResultData> partyResultDatas;

    private final Integer countTotalPartyVotes;

    private final int quotient;

    public ProportionalVotingResultData(Set<PartyResultData> partyResultDatas, Integer countTotalPartyVotes, int quotient) {
        this.partyResultDatas = partyResultDatas;
        this.countTotalPartyVotes = countTotalPartyVotes;
        this.quotient = quotient;
    }

    public Set<PartyResultData> getPartyResultDatas() {
        return partyResultDatas;
    }

    public Integer getCountTotalPartyVotes() {
        return countTotalPartyVotes;
    }

    public int getQuotient() {
        return quotient;
    }
}
