package ch.bfh.ti.advancedweb.voting.interal;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.UserRepository;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResultRepository;
import ch.bfh.ti.advancedweb.voting.domain.voting.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
public class DefaultVotingService implements VotingService {

    private final MajorzVotingRepository majorzVotingRepository;

    private final ProporzVotingRepository proporzVotingRepository;

    private final VotingResultRepository votingResultRepository;

    private final UserRepository userRepository;

    private static final Sort SORT = new Sort(Sort.DEFAULT_DIRECTION, "created", "votingId");

    @Inject
    public DefaultVotingService(MajorzVotingRepository majorzVotingRepository, ProporzVotingRepository proporzVotingRepository, VotingResultRepository votingResultRepository, UserRepository userRepository) {
        this.majorzVotingRepository = majorzVotingRepository;
        this.proporzVotingRepository = proporzVotingRepository;
        this.votingResultRepository = votingResultRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Map<MajorzVoting, Boolean> getCurrentMajorzVotings(String userId) {
        Map<MajorzVoting, Boolean> resultMap = new LinkedHashMap<>();
        final List<MajorzVoting> majorzVotings = majorzVotingRepository.findAll(SORT);
        for (MajorzVoting majorzVoting : majorzVotings) {
            majorzVoting.getMajorzCandidates().size();
            final VotingResult resultByUser = votingResultRepository.findCandidateVotingResultByUser(userId, majorzVoting.getVotingId());
            resultMap.put(majorzVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public Map<ProporzVoting, Boolean> getCurrentProporzVotings(String userId) {
        Map<ProporzVoting, Boolean> resultMap = new LinkedHashMap<>();
        final List<ProporzVoting> proporzVotings = proporzVotingRepository.findAll(SORT);
        for (ProporzVoting proporzVoting : proporzVotings) {
            proporzVoting.getPorporzCandidates().size();
            final VotingResult resultByUser = votingResultRepository.findCandidateVotingResultByUser(userId, proporzVoting.getVotingId());
            resultMap.put(proporzVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public void majorzVote(String userId, String majorzVotingId, Set<Candidate> candidates) {
        final User user = getUser(userId);
        final MajorzVoting majorzVoting = getMajorzVoting(majorzVotingId);
        checkExistingVotingResult(userId, majorzVotingId);
        final CandidateVotingResult proporzVotingResult = new CandidateVotingResult(majorzVoting, new ArrayList<>(candidates), user);
        votingResultRepository.save(proporzVotingResult);
    }

    private void checkExistingVotingResult(String userId, String majorzVotingId) {
        final VotingResult candidateVotingResultByUser = votingResultRepository.findCandidateVotingResultByUser(userId, majorzVotingId);
        if (candidateVotingResultByUser != null) {
            throw new IllegalStateException("User has already voted for " + majorzVotingId);
        }
    }


    @Override
    public void proporzVote(String userId, String proporzVotingId, List<Candidate> candidates) {
        final User user = getUser(userId);
        final ProporzVoting proporzVoting = getProporzVoting(proporzVotingId);
        checkExistingVotingResult(userId, proporzVotingId);
        final CandidateVotingResult proporzVotingResult = new CandidateVotingResult(proporzVoting, candidates, user);
        votingResultRepository.save(proporzVotingResult);
    }

    @Override
    public VotingResult getVotingResultForUser(String userId, Voting voting) {
        final User user = getUser(userId);
        return votingResultRepository.findCandidateVotingResultByUser(user.getId(), voting.getVotingId());
    }


    private User getUser(String userId) {
        final User user = userRepository.findOne(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return user;
    }

    private ProporzVoting getProporzVoting(String proporzVotingId) {
        final ProporzVoting proporzVoting = proporzVotingRepository.findOne(proporzVotingId);
        if (proporzVoting == null) {
            throw new IllegalArgumentException("Could not find Proporz Voting: " + proporzVoting);
        }
        return proporzVoting;
    }


    private MajorzVoting getMajorzVoting(String majorzVotingId) {
        final MajorzVoting majorzVoting = majorzVotingRepository.findOne(majorzVotingId);
        if (majorzVoting == null) {
            throw new IllegalArgumentException("Could not find Majorz Voting: " + majorzVotingId);
        }
        return majorzVoting;
    }
}
