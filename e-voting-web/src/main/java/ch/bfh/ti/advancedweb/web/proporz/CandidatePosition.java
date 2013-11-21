package ch.bfh.ti.advancedweb.web.proporz;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;

public class CandidatePosition {
    private Candidate candidate;

    private boolean customCandidate;

    private String firstName;

    private String lastName;

    private String partyName;

    public CandidatePosition(Candidate candidate, boolean customCandidate) {
        this.candidate = candidate;
        this.customCandidate = customCandidate;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public boolean isCustomCandidate() {

        return customCandidate;
    }

    public void applyCustomCandidate() {
        this.candidate = new Candidate(firstName, lastName, partyName);
        this.firstName = null;
        this.lastName = null;
        this.partyName = null;
    }

    public void setCustomCandidate(boolean customCandidate) {
        this.customCandidate = customCandidate;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
