package ch.bfh.ti.advancedweb.evoting.interal;

import ch.bfh.ti.advancedweb.evoting.CandidateResultData;
import ch.bfh.ti.advancedweb.evoting.ReferendumResultData;
import ch.bfh.ti.advancedweb.evoting.VotingAdminService;
import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.result.CandidateVotingResultRepository;
import ch.bfh.ti.advancedweb.evoting.domain.result.ReferendumVotingResultRepository;
import ch.bfh.ti.advancedweb.evoting.domain.result.VotingResultRepository;
import ch.bfh.ti.advancedweb.evoting.domain.voting.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
class DefaultVotingAdminService implements VotingAdminService {

    private final VotingRepository votingRepository;

    private final ReferendumVotingResultRepository referendumVotingResultRepository;

    private final CandidateVotingResultRepository candidateVotingResultRepository;

    @Inject
    public DefaultVotingAdminService(VotingRepository votingRepository, VotingResultRepository votingResultRepository, ReferendumVotingResultRepository referendumVotingResultRepository, CandidateVotingResultRepository candidateVotingResultRepository) {
        this.votingRepository = votingRepository;
        this.referendumVotingResultRepository = referendumVotingResultRepository;
        this.candidateVotingResultRepository = candidateVotingResultRepository;
    }

    @Override
    public void stopAllCurrentVotings() {
        final List<Voting> votings = votingRepository.findAll();
        for (Voting voting : votings) {
            voting.setOpen(false);
        }
    }

    @Override
    public List<Voting> getCurrentVotings() {
        return votingRepository.findAll(new Sort("createDate"));
    }

    @Override
    public Set<CandidateResultData> getCandidateResults(String votingId) {
        final Voting voting = votingRepository.findOne(votingId);
        List<CandidateResultData> result = new ArrayList<>();
        final VotingType votingType = voting.getVotingType();
        if (votingType.equals(VotingType.MAJORITY)) {
            count(votingId, result, new ArrayList<>(((MajorityVoting) voting).getMajorityCandidates()));
        } else if (votingType.equals(VotingType.PROPORTIONAL)) {
            ProportionalVoting proportionalVoting = (ProportionalVoting) voting;
            count(votingId, result, proportionalVoting.getProportionalCandidates());
        } else {
            throw new IllegalStateException("Voting having voting type '" + votingType + "' is not supported");
        }

        Collections.sort(result);

        return new LinkedHashSet<>(result);
    }

    @Override
    public ReferendumResultData getReferendumResult(String referendumVotingId) {
        final int acceptCount = referendumVotingResultRepository.countAcceptVotes(referendumVotingId);
        final int rejectCount = referendumVotingResultRepository.countRejectVotes(referendumVotingId);
        return new ReferendumResultData(acceptCount, rejectCount);
    }

    private void count(String votingId, Collection<CandidateResultData> result, List<Candidate> candidateList) {
        for (Candidate candidate : candidateList) {
            final int votes = candidateVotingResultRepository.countVotesForCandidate(votingId, candidate.getCandidateId());
            result.add(new CandidateResultData(candidate, votes));
        }
    }
}
