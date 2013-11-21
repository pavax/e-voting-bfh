package ch.bfh.ti.advancedweb.web.proporz;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Scope("request")
public class ProporzController {

    private final ProporzModel proporzModel;

    private final VotingService votingService;

    private final CurrentUserModel currentUserModel;

    @Inject
    public ProporzController(ProporzModel proporzModel, VotingService votingService, CurrentUserModel currentUserModel) {
        this.proporzModel = proporzModel;
        this.votingService = votingService;
        this.currentUserModel = currentUserModel;
    }


    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (proporzModel.isAlreadyVoted()) {
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingResultForUser(currentUserModel.getUserId(), proporzModel.getSelectedProporzVoting());
                proporzModel.getCandidatePositions().clear();
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    selectCandidate(candidate);
                }
            }
        }
    }

    public void selectCandidate(Candidate candidate) {
        final List<CandidatePosition> selectedCandidates = proporzModel.getCandidatePositions();
        final Map<String, List<Candidate>> partyCandidatesMap = proporzModel.getPartyCandidatesMap();

        if (proporzModel.isMaxPositionsFilled()) {
            MessageUtils.addWarnMessage("proporz.maxPositionsFilled");
            return;
        }

        final int i = Collections.frequency(selectedCandidates, candidate);
        if (i == 2) {
            MessageUtils.addWarnMessage("proporz.candidate.alreadyVoted");
            return;
        }
        for (CandidatePosition selectedCandidate : selectedCandidates) {
            if (selectedCandidate.getCandidate() == null) {
                selectedCandidate.setCandidate(candidate);
                selectedCandidate.setCustomCandidate(false);
                break;
            }
        }
    }

    public void removedSelectedCandidate(int index) {
        final List<CandidatePosition> proporzModelSelectedCandidates = proporzModel.getCandidatePositions();
        final CandidatePosition selectedCandidate = proporzModelSelectedCandidates.get(index);
        selectedCandidate.setCandidate(null);
        selectedCandidate.setCustomCandidate(false);
    }

    public void selectAllFromParty(String partyName) {
        proporzModel.getCandidatePositions().clear();
        final Map<String, List<Candidate>> partyCandidatesMap = proporzModel.getPartyCandidatesMap();
        final List<Candidate> candidateList = partyCandidatesMap.get(partyName);
        proporzModel.resetSelectedCandidates();
        final List<CandidatePosition> selectedCandidates = proporzModel.getCandidatePositions();
        for (Candidate candidate : candidateList) {
            for (CandidatePosition selectedCandidate : selectedCandidates) {
                if (selectedCandidate.getCandidate() == null) {
                    selectedCandidate.setCandidate(candidate);
                    break;
                }
            }
        }
    }


    public void voteAction() {
        //
    }

}
