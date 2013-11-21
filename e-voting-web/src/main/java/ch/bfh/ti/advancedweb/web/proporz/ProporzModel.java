package ch.bfh.ti.advancedweb.web.proporz;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProporzVoting;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("session")
public class ProporzModel {

    private String proporzVotingId;

    private ProporzVoting selectedProporzVoting;

    private boolean alreadyVoted;

    private List<CandidatePosition> candidatePositions = new ArrayList<>();

    private Map<String, List<Candidate>> partyCandidatesMap = new HashMap<>();

    public void clear() {
        partyCandidatesMap.clear();
        candidatePositions.clear();
        alreadyVoted = false;
        selectedProporzVoting = null;
        proporzVotingId = null;
    }

    public void setSelectedProporzVoting(ProporzVoting selectedProporzVoting) {
        clear();
        this.proporzVotingId = selectedProporzVoting.getVotingId();
        this.selectedProporzVoting = selectedProporzVoting;
        final Set<Candidate> porporzCandidates = selectedProporzVoting.getPorporzCandidates();
        initPartyCandidateMap(porporzCandidates);
        resetSelectedCandidates();
    }

    public void resetSelectedCandidates() {
        for (int i = 0; i < selectedProporzVoting.getOpenPositions(); i++) {
            this.candidatePositions.add(new CandidatePosition(null, false));
        }
    }

    private void initPartyCandidateMap(Set<Candidate> porporzCandidates) {
        for (Candidate proporzCandidate : porporzCandidates) {
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

    public boolean isMaxPositionsFilled() {
        return countSelectedCandidates() == selectedProporzVoting.getOpenPositions();
    }

    public int countSelectedCandidates() {
        int i = 0;
        for (CandidatePosition selectedCandidate : candidatePositions) {
            if (selectedCandidate.getCandidate() != null) {
                i++;
            }
        }
        return i;
    }

    public boolean candidateDisabled(Candidate candidate) {
        List<Candidate> candidates = new ArrayList<>();
        for (CandidatePosition selectedCandidate : candidatePositions) {
            candidates.add(selectedCandidate.getCandidate());
        }
        return Collections.frequency(candidates, candidate) == 2;
    }


    public Map<String, List<Candidate>> getPartyCandidatesMap() {
        return partyCandidatesMap;
    }

    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }

    public void setAlreadyVoted(boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }

    public ProporzVoting getSelectedProporzVoting() {
        return selectedProporzVoting;
    }

    public List<CandidatePosition> getCandidatePositions() {
        return candidatePositions;
    }
}
