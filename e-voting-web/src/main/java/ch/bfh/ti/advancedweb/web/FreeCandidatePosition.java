package ch.bfh.ti.advancedweb.web;

public class FreeCandidatePosition {

    private boolean selectCustomCandidate;

    private String customCandidateName;

    public String getCustomCandidateName() {
        return customCandidateName;
    }

    public void setCustomCandidateName(String customCandidateName) {
        this.customCandidateName = customCandidateName;
    }

    public boolean isSelectCustomCandidate() {
        return selectCustomCandidate;
    }

    public void setSelectCustomCandidate(boolean selectCustomCandidate) {
        this.selectCustomCandidate = selectCustomCandidate;
    }
}
