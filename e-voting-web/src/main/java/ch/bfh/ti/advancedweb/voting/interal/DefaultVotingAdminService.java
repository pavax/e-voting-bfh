package ch.bfh.ti.advancedweb.voting.interal;

import ch.bfh.ti.advancedweb.voting.CandidateResult;
import ch.bfh.ti.advancedweb.voting.VotingAdminService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResultRepository;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResultRepository;
import ch.bfh.ti.advancedweb.voting.domain.voting.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
public class DefaultVotingAdminService implements VotingAdminService {

    private final VotingRepository votingRepository;

    private final CandidateVotingResultRepository candidateVotingResultRepository;

    @Inject
    public DefaultVotingAdminService(VotingRepository votingRepository, VotingResultRepository votingResultRepository, CandidateVotingResultRepository candidateVotingResultRepository) {
        this.votingRepository = votingRepository;
        this.candidateVotingResultRepository = candidateVotingResultRepository;
    }

    @Override
    public void stopAllVotings() {
        final List<Voting> votings = votingRepository.findAll();
        for (Voting voting : votings) {
            voting.setOpen(false);
        }
    }

    @Override
    public List<Voting> getCurrentVotings() {
        return votingRepository.findAll(new Sort("created"));
    }

    @Override
    public Set<CandidateResult> getCandidateResults(String votingId) {
        final Voting voting = votingRepository.findOne(votingId);
        List<CandidateResult> result = new ArrayList<>();
        final VotingType votingType = voting.getVotingType();
        if (votingType.equals(VotingType.MAYORZ)) {
            count(votingId, result, new ArrayList<>(((MajorityVoting) voting).getMajorzCandidates()));
        } else if (votingType.equals(VotingType.PROPORTZ)) {
            ProportionalVoting proportionalVoting = (ProportionalVoting) voting;
            count(votingId, result, proportionalVoting.getPorporzCandidates());
        } else {
            throw new IllegalStateException("Voting having voting type '" + votingType + "' is not supported");
        }

        Collections.sort(result);

        return new LinkedHashSet<>(result);
    }

    private void count(String votingId, Collection<CandidateResult> result, List<Candidate> candidateList) {
        for (Candidate candidate : candidateList) {
            final int votes = candidateVotingResultRepository.countVotesForCandidate(votingId, candidate.getCandidateId());
            result.add(new CandidateResult(candidate, votes));
        }
    }
}
