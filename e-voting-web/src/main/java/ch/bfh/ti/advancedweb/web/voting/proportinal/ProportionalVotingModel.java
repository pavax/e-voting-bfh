package ch.bfh.ti.advancedweb.web.voting.proportinal;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.web.voting.AbstractCandidateSelectionModel;
import ch.bfh.ti.advancedweb.web.voting.CandidatePosition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("session")
public class ProportionalVotingModel extends AbstractCandidateSelectionModel<ProportionalVoting> {

    private Map<String, List<Candidate>> partyCandidatesMap = new HashMap<>();

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
        for (Candidate proporzCandidate : voting.getPorporzCandidates()) {
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
}
