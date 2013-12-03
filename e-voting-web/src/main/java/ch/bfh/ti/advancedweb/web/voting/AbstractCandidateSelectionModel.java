package ch.bfh.ti.advancedweb.web.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.voting.AbstractCandidateVoting;
import ch.bfh.ti.advancedweb.web.votinglist.VotingState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCandidateSelectionModel<T extends AbstractCandidateVoting> {

    private T voting;

    private String votingId;

    private VotingState votingState;

    private boolean alreadyVoted;

    private List<CandidatePosition> candidatePositions = new ArrayList<>();

    public void setVoting(T voting) {
        clear();
        this.voting = voting;
        this.votingId = voting.getVotingId();
        this.initCandidatePositions();
        this.initPossibleCandidates(voting);
    }

    public void clear() {
        candidatePositions.clear();
        votingState = null;
        voting = null;
        votingId = null;
    }


    public void addCandidateToTheNextFreePosition(Candidate candidate) {
        for (CandidatePosition candidatePosition : candidatePositions) {
            if (candidatePosition.getCandidate() == null) {
                candidatePosition.setCandidate(candidate);
                break;
            }
        }
    }

    public void initCandidatePositions() {
        this.candidatePositions.clear();
        for (int i = 0; i < voting.getOpenPositions(); i++) {
            this.candidatePositions.add(new CandidatePosition(null));
        }
    }

    public boolean isMaxPositionsFilled() {
        int counter = 0;
        for (CandidatePosition candidatePosition : candidatePositions) {
            if (candidatePosition.getCandidate() != null) {
                counter++;
            }
        }
        return counter >= voting.getOpenPositions();
    }

    public abstract boolean candidateDisabled(Candidate candidate);

    public abstract void initPossibleCandidates(T voting);

    public List<CandidatePosition> getCandidatePositions() {
        return Collections.unmodifiableList(candidatePositions);
    }

    public T getVoting() {
        return voting;
    }

    public VotingState getVotingState() {
        return votingState;
    }

    public void setVotingState(VotingState votingState) {
        this.votingState = votingState;
        alreadyVoted = this.votingState.equals(VotingState.VOTED);
    }

    public String getVotingId() {
        return votingId;
    }

    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }
}
