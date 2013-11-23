package ch.bfh.ti.advancedweb.web.voting.proporz;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import ch.bfh.ti.advancedweb.web.utils.MessageUtils;
import ch.bfh.ti.advancedweb.web.voting.CandidatePosition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
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
                final CandidateVotingResult votingResultForUser = (CandidateVotingResult) votingService.getVotingResultForUser(currentUserModel.getUserId(), proporzModel.getVoting());
                for (Candidate candidate : votingResultForUser.getVotedCandidates()) {
                    selectCandidate(candidate);
                }
            }
        }
    }

    public void selectCandidate(Candidate candidate) {
        if (proporzModel.isMaxPositionsFilled()) {
            MessageUtils.addWarnMessage("proporz.maxPositionsFilled");
            return;
        }

        if (proporzModel.candidateDisabled(candidate)) {
            MessageUtils.addWarnMessage("proporz.candidate.alreadyVoted");
            return;
        }

        proporzModel.addCandidateToTheNextFreePosition(candidate);
    }

    public void removedSelectedCandidate(int index) {
        proporzModel.getCandidatePositions().get(index).setCandidate(null);
    }

    public void selectAllFromParty(String partyName) {
        proporzModel.initCandidatePositions();
        final Map<String, List<Candidate>> partyCandidatesMap = proporzModel.getPartyCandidatesMap();
        final List<Candidate> candidateList = partyCandidatesMap.get(partyName);
        for (Candidate candidate : candidateList) {
            proporzModel.addCandidateToTheNextFreePosition(candidate);
        }
    }


    public String voteAction() {
        List<Candidate> selectedCanidates = new ArrayList<>();
        for (CandidatePosition candidatePosition : proporzModel.getCandidatePositions()) {
            if (candidatePosition.getCandidate() != null) {

                selectedCanidates.add(candidatePosition.getCandidate());
            }
        }
        votingService.proporzVote(currentUserModel.getUserId(), proporzModel.getVotingId(), selectedCanidates);
        proporzModel.clear();
        return "index.xhtml?faces-redirect=true";
    }

}
