package ch.bfh.ti.advancedweb.evoting.web.voting.proportinal;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.evoting.web.voting.AbstractCandidateSelectionModel;
import ch.bfh.ti.advancedweb.evoting.web.voting.CandidatePosition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * The ProportionalVotingModel containing all Fields and Methods in order to select Candidates for a Proportional Voting
 */
@Component
@Scope("session")
public class ProportionalVotingModel extends AbstractCandidateSelectionModel<ProportionalVoting> {

    private Map<String, List<Candidate>> partyCandidatesMap = new HashMap<>();

    private String partyListName;

    @Override
    public void clear() {
        super.clear();
        partyCandidatesMap.clear();
    }

    @Override
    public boolean candidateDisabled(Candidate candidate) {
        return Collections.frequency(getCandidatePositions(), new CandidatePosition(candidate)) >= 2;
    }

    @Override
    public void initPossibleCandidates(ProportionalVoting voting) {
        for (Candidate proporzCandidate : voting.getProportionalCandidates()) {
            final String partyName = proporzCandidate.getPartyName();
            if (partyCandidatesMap.containsKey(partyName)) {
                final List<Candidate> candidates = partyCandidatesMap.get(partyName);
                candidates.add(proporzCandidate);
            } else {
                List<Candidate> partyCandidateList = new ArrayList<>();
                partyCandidateList.add(proporzCandidate);
                partyCandidatesMap.put(partyName, partyCandidateList);
            }
        }
    }

    public Map<String, List<Candidate>> getPartyCandidatesMap() {
        return partyCandidatesMap;
    }

    public String getPartyListName() {
        return partyListName;
    }

    public void setPartyListName(String partyListName) {
        this.partyListName = partyListName;
    }

    public List<String> getPossibleParties() {
        Set<String> partyNames = new HashSet<>();
        for (CandidatePosition candidatePosition : getCandidatePositions()) {
            if (candidatePosition.getCandidate() != null){
                partyNames.add(candidatePosition.getCandidate().getPartyName());
            }
        }
        return new ArrayList<>(partyNames);
    }
}
