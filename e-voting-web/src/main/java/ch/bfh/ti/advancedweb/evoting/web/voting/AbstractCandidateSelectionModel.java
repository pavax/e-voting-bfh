package ch.bfh.ti.advancedweb.evoting.web.voting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.voting.AbstractCandidateVoting;
import ch.bfh.ti.advancedweb.evoting.web.votinglist.VotingState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides common Fields and Methods for an Candidate Selection
 *
 * @param <T> A concrete Type of AbstractCandidateVoting (MajorityVoting or ProportionalVoting)
 */
public abstract class AbstractCandidateSelectionModel<T extends AbstractCandidateVoting> {

    private T voting;

    private String votingId;

    private VotingState votingState;

    private boolean alreadyVoted;

    private List<CandidatePosition> candidatePositions = new ArrayList<>();

    /**
     * Initializes the current-model according to the given voting
     *
     * @param voting the selected Voting to handle
     */
    public void setVoting(T voting) {
        clear();
        this.voting = voting;
        this.votingId = voting.getVotingId();
        this.initCandidatePositions();
        this.initPossibleCandidates(voting);
    }

    /**
     * Clears all relevant Fields
     */
    public void clear() {
        candidatePositions.clear();
        votingState = null;
        voting = null;
        votingId = null;
    }


    /**
     * Adds the selected Candidate to the next available CandidatePosition
     *
     * @param candidate the candidate to select
     */
    public void addCandidateToTheNextFreePosition(Candidate candidate) {
        for (CandidatePosition candidatePosition : candidatePositions) {
            if (candidatePosition.getCandidate() == null) {
                candidatePosition.setCandidate(candidate);
                break;
            }
        }
    }

    /**
     * Initializes the candidatePosition according to the available positions
     */
    public void initCandidatePositions() {
        this.candidatePositions.clear();
        for (int i = 0; i < voting.getOpenPositions(); i++) {
            this.candidatePositions.add(new CandidatePosition(null));
        }
    }

    /**
     * Checks if all available positions are already filled
     *
     * @return true if all positions are filled
     */
    public boolean isMaxPositionsFilled() {
        int counter = 0;
        for (CandidatePosition candidatePosition : candidatePositions) {
            if (candidatePosition.getCandidate() != null) {
                counter++;
            }
        }
        return counter >= voting.getOpenPositions();
    }

    /**
     * Checks if the provided Candidate is disabled
     * <p/>
     * Might be the case if the candidate is already voted or is voted twice
     *
     * @param candidate the Candidate to check against
     * @return true if the given Candidate is disabled
     */
    public abstract boolean candidateDisabled(Candidate candidate);

    /**
     * Initializes the possible Candidate for the Model according to the given Voting
     *
     * @param voting containing the possible Candidates
     */
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

    /**
     * Checks if the user already voted (Saved Vote to the Database)
     *
     * @return true if the User already voted for the current voting
     */
    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }
}
