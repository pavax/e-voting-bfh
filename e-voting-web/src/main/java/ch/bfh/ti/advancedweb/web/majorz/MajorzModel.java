package ch.bfh.ti.advancedweb.web.majorz;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVoting;
import ch.bfh.ti.advancedweb.web.FreeCandidatePosition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class MajorzModel {

    private String majorzVotingId;

    private MajorzVoting selectedMajorzVoting;

    private boolean alreadyVoted;

    private List<Candidate> possibleCandidates = new ArrayList<>();

    private List<Candidate> selectedCandidates = new ArrayList<>();

    private List<FreeCandidatePosition> freeCandidatePositions = new ArrayList<>();

    public void clear() {
        possibleCandidates.clear();
        selectedCandidates.clear();
        alreadyVoted = false;
        freeCandidatePositions.clear();
        selectedMajorzVoting = null;
        majorzVotingId = null;
    }

    public String getMajorzVotingId() {
        return majorzVotingId;
    }

    public void setMajorzVotingId(String majorzVotingId) {
        this.majorzVotingId = majorzVotingId;
    }

    public MajorzVoting getSelectedMajorzVoting() {
        return selectedMajorzVoting;
    }

    public void setSelectedMajorzVoting(MajorzVoting selectedMajorzVoting) {
        clear();
        this.majorzVotingId = selectedMajorzVoting.getVotingId();
        this.selectedMajorzVoting = selectedMajorzVoting;
        this.possibleCandidates.addAll(selectedMajorzVoting.getMajorzCandidates());
    }

    public List<Candidate> getPossibleCandidates() {
        return possibleCandidates;
    }

    public List<Candidate> getSelectedCandidates() {
        return selectedCandidates;
    }

    public boolean isMaxPositionsFilled() {
        return selectedCandidates.size() == selectedMajorzVoting.getOpenPositions();
    }

    public List<FreeCandidatePosition> getFreeCandidatePositions() {
        final int countFreePositions = selectedMajorzVoting.getOpenPositions() - selectedCandidates.size();
        this.freeCandidatePositions.clear();
        for (int i = 0; i < countFreePositions; i++) {
            this.freeCandidatePositions.add(new FreeCandidatePosition());
        }
        return freeCandidatePositions;
    }

    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }

    public void setAlreadyVoted(boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }


}
