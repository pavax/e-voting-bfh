package ch.bfh.ti.advancedweb.evoting.domain.result;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.VotingType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.*;

@Entity
public class CandidateVotingResult extends VotingResult {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Candidate> votedCandidates = new ArrayList<>();

    private String partyListName;

    // (Kandidaten- und Zusatzstimmen = Parteistimmen)
    private int partyListVotes = 0;

    /**
     * @param proportionalVoting
     * @param votedCandidates
     * @param partyListName      Mit der Partei- bzw. Listenbezeichnung geben die Wählerinnen und Wähler an, welche Partei sie grundsätzlich unterstützen wollen. Allfällige leere Linien auf dem Wahlzettel werden zu Zusatzstimmen, welche die Sitzverteilung mitbeeinflussen. Ohne Listenbezeichnung bleiben die leeren Kandidierenden-Linien ohne Wirkung.
     * @param voter
     */
    public CandidateVotingResult(ProportionalVoting proportionalVoting, List<Candidate> votedCandidates, String partyListName, User voter) {
        super(proportionalVoting, voter);
        checkAtLeastOneCandidate(votedCandidates);
        validateProportionalVotingType(proportionalVoting);
        checkVotedCandidateFrequency(votedCandidates);
        this.votedCandidates.addAll(votedCandidates);
        this.partyListName = partyListName;
        if (partyListName != null && !partyListName.isEmpty()) {
            countCandidatePartyVotes(votedCandidates, partyListName);
            countAdditionalPartyVotes(proportionalVoting, votedCandidates);
        }
    }

    public CandidateVotingResult(MajorityVoting majorityVoting, Set<Candidate> votedCandidates, User voter) {
        super(majorityVoting, voter);
        checkAtLeastOneCandidate(votedCandidates);
        validateMajorityVotingType(majorityVoting);
        this.votedCandidates.addAll(votedCandidates);
        this.partyListName = null;
    }

    private void checkAtLeastOneCandidate(Collection<Candidate> votedCandidates) {
        if (votedCandidates.isEmpty()) {
            throw new IllegalArgumentException("At least one candidate must be selected");
        }
    }

    private void countCandidatePartyVotes(List<Candidate> votedCandidates, String partyListName) {
        for (Candidate votedCandidate : votedCandidates) {
            if (votedCandidate.getPartyName().equals(partyListName)) {
                partyListVotes++;
            }
        }
    }

    /**
     * Leere Linien zählen für diese Partei
     *
     * @param proportionalVoting
     * @param votedCandidates
     */
    private void countAdditionalPartyVotes(ProportionalVoting proportionalVoting, List<Candidate> votedCandidates) {
        if (votedCandidates.size() != proportionalVoting.getOpenPositions()) {
            int additionalPartyListVotes = proportionalVoting.getOpenPositions() - votedCandidates.size();
            partyListVotes = partyListVotes + additionalPartyListVotes;
        }
    }

    private void validateProportionalVotingType(Voting voting) {
        if (!voting.getVotingType().equals(VotingType.PROPORTIONAL)) {
            throw new IllegalArgumentException("Only Voting of Type PROPORTIONAL are allowed in combination with a List");
        }
    }

    private void validateMajorityVotingType(Voting voting) {
        if (!voting.getVotingType().equals(VotingType.MAJORITY)) {
            throw new IllegalArgumentException("Only Voting of Type MAJORITY are allowed in combination with a Set");
        }
    }

    private void checkVotedCandidateFrequency(List<Candidate> votedCandidates) {
        for (Candidate votedCandidate : votedCandidates) {
            if (Collections.frequency(votedCandidates, votedCandidate) > 2) {
                throw new IllegalArgumentException("A Candidate can max. occur 2 times within the List");
            }
        }
    }

    protected CandidateVotingResult() {
        // FOR JPA
        super();
    }

    public List<Candidate> getVotedCandidates() {
        return Collections.unmodifiableList(this.votedCandidates);
    }

    public int getPartyListVotes() {
        return partyListVotes;
    }

    public String getPartyListName() {
        return partyListName;
    }
}
