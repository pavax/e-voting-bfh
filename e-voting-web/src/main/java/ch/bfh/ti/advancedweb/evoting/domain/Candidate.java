package ch.bfh.ti.advancedweb.evoting.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Candidate {

    @Id
    private String candidateId;

    private String firstName;

    private String lastName;

    private String partyName;

    public Candidate( String firstName, String lastName, String partyName) {
        this.candidateId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.partyName = partyName;
    }

    protected Candidate() {
        // FOR JPA
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPartyName() {
        return partyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate)) return false;

        Candidate candidate = (Candidate) o;

        if (candidateId != null ? !candidateId.equals(candidate.candidateId) : candidate.candidateId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return candidateId != null ? candidateId.hashCode() : 0;
    }
}
