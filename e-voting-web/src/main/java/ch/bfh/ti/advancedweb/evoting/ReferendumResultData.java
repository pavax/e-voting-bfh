package ch.bfh.ti.advancedweb.evoting;

public class ReferendumResultData {

    private int acceptCount;

    private int rejectCount;

    public ReferendumResultData(int acceptCount, int rejectCount) {
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
