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

    private final MajorityVotingRepository majorityVotingRepository;

    private final ProporzVotingRepository proporzVotingRepository;

    private final ReferendumVotingRepository referendumVotingRepository;

    private final VotingResultRepository votingResultRepository;

    private final UserRepository userRepository;

    private static final Sort SORT = new Sort(Sort.DEFAULT_DIRECTION, "created", "votingId");

    @Inject
    public DefaultVotingService(MajorityVotingRepository majorityVotingRepository, ProporzVotingRepository proporzVotingRepository, ReferendumVotingRepository referendumVotingRepository, VotingResultRepository votingResultRepository, UserRepository userRepository) {
        this.majorityVotingRepository = majorityVotingRepository;
        this.proporzVotingRepository = proporzVotingRepository;
        this.referendumVotingRepository = referendumVotingRepository;
        this.votingResultRepository = votingResultRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Map<MajorityVoting, Boolean> getCurrentMajorityVotings(String userId) {
        Map<MajorityVoting, Boolean> resultMap = new LinkedHashMap<>();
        final List<MajorityVoting> majorityVotings = majorityVotingRepository.findAll(SORT);
        for (MajorityVoting majorityVoting : majorityVotings) {
            majorityVoting.getMajorzCandidates().size();
            final VotingResult resultByUser = votingResultRepository.findVotingResultFromUser(userId, majorityVoting.getVotingId());
            resultMap.put(majorityVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public Map<ProportionalVoting, Boolean> getCurrentProportionalVotings(String userId) {
        Map<ProportionalVoting, Boolean> resultMap = new LinkedHashMap<>();
        final List<ProportionalVoting> proportionalVotings = proporzVotingRepository.findAll(SORT);
        for (ProportionalVoting proportionalVoting : proportionalVotings) {
            proportionalVoting.getPorporzCandidates().size();
            final VotingResult resultByUser = votingResultRepository.findVotingResultFromUser(userId, proportionalVoting.getVotingId());
            resultMap.put(proportionalVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public Map<ReferendumVoting, Boolean> getCurrentReferendumVotings(String userId) {
        Map<ReferendumVoting, Boolean> resultMap = new LinkedHashMap<>();
        final List<ReferendumVoting> referendumVotings = referendumVotingRepository.findAll(SORT);
        for (ReferendumVoting referendumVoting : referendumVotings) {
            final VotingResult resultByUser = votingResultRepository.findVotingResultFromUser(userId, referendumVoting.getVotingId());
            resultMap.put(referendumVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public void saveMajorityVote(String userId, String majorityVotingId, Set<Candidate> candidates) {
        final User user = getUser(userId);
        final MajorityVoting majorityVoting = getMajorityVoting(majorityVotingId);
        checkExistingVotingResult(userId, majorityVotingId);
        final CandidateVotingResult proporzVotingResult = new CandidateVotingResult(majorityVoting, new ArrayList<>(candidates), user);
        votingResultRepository.save(proporzVotingResult);
    }

    private void checkExistingVotingResult(String userId, String majorityVotingId) {
        final VotingResult candidateVotingResultByUser = votingResultRepository.findVotingResultFromUser(userId, majorityVotingId);
        if (candidateVotingResultByUser != null) {
            throw new IllegalStateException("User has already voted for " + majorityVotingId);
        }
    }

    @Override
    public void saveProportionalVote(String userId, String proportionalVotingId, List<Candidate> candidates) {
        final User user = getUser(userId);
        final ProportionalVoting proportionalVoting = getProportionalVoting(proportionalVotingId);
        checkExistingVotingResult(userId, proportionalVotingId);
        final CandidateVotingResult proporzVotingResult = new CandidateVotingResult(proportionalVoting, candidates, user);
        votingResultRepository.save(proporzVotingResult);
    }

    @Override
    public VotingResult getVotingsFromUser(String userId, Voting voting) {
        final User user = getUser(userId);
        return votingResultRepository.findVotingResultFromUser(user.getId(), voting.getVotingId());
    }


    private User getUser(String userId) {
        final User user = userRepository.findOne(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return user;
    }

    private ProportionalVoting getProportionalVoting(String proportionalVotingId) {
        final ProportionalVoting proportionalVoting = proporzVotingRepository.findOne(proportionalVotingId);
        if (proportionalVoting == null) {
            throw new IllegalArgumentException("Could not find Proportional Voting: " + proportionalVoting);
        }
        return proportionalVoting;
    }


    private MajorityVoting getMajorityVoting(String majorityVotingId) {
        final MajorityVoting majorityVoting = majorityVotingRepository.findOne(majorityVotingId);
        if (majorityVoting == null) {
            throw new IllegalArgumentException("Could not find Majority Voting: " + majorityVotingId);
        }
        return majorityVoting;
    }
}
