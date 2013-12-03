package ch.bfh.ti.advancedweb.voting;

public class ReferendumResult {

    private int acceptCount;

    private int rejectCount;

    public ReferendumResult(int acceptCount, int rejectCount) {
        this.acceptCount = acceptCount;
        this.rejectCount = rejectCount;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public int getRejectCount() {
        return rejectCount;
    }


}
