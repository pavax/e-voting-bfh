package ch.bfh.ti.advancedweb.evoting.interal;

import ch.bfh.ti.advancedweb.evoting.VotingService;
import ch.bfh.ti.advancedweb.evoting.VotingStoppedException;
import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.UserRepository;
import ch.bfh.ti.advancedweb.evoting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.evoting.domain.result.ReferendumVotingResult;
import ch.bfh.ti.advancedweb.evoting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.evoting.domain.result.VotingResultRepository;
import ch.bfh.ti.advancedweb.evoting.domain.voting.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
class DefaultVotingService implements VotingService {

    private final MajorityVotingRepository majorityVotingRepository;

    private final ProporzVotingRepository proporzVotingRepository;

    private final ReferendumVotingRepository referendumVotingRepository;

    private final VotingResultRepository votingResultRepository;

    private final UserRepository userRepository;

    private static final Sort SORT = new Sort(Sort.DEFAULT_DIRECTION, "createDate", "votingId");

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
            majorityVoting.getMajorityCandidates().size();
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
            proportionalVoting.getProportionalCandidates().size();
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
    public void saveMajorityVote(String userId, String majorityVotingId, Set<Candidate> candidates) throws VotingStoppedException {
        final User user = getUser(userId);
        final MajorityVoting majorityVoting = getMajorityVoting(majorityVotingId);
        checkIsVotingOpen(majorityVotingId, majorityVoting.isOpen());
        checkExistingVotingResult(userId, majorityVotingId);
        votingResultRepository.save(new CandidateVotingResult(majorityVoting, candidates, user));
    }

    @Override
    public void saveProportionalVote(String userId, String proportionalVotingId, List<Candidate> candidates) throws VotingStoppedException {
        final User user = getUser(userId);
        final ProportionalVoting proportionalVoting = getProportionalVoting(proportionalVotingId);
        checkIsVotingOpen(proportionalVotingId, proportionalVoting.isOpen());
        checkExistingVotingResult(userId, proportionalVotingId);
        votingResultRepository.save(new CandidateVotingResult(proportionalVoting, candidates, user));
    }

    @Override
    public void saveReferendumVote(String userId, String referendumVotingId, Boolean acceptReferendum) throws VotingStoppedException {
        final User user = getUser(userId);
        final ReferendumVoting referendumVoting = getReferendumVoting(referendumVotingId);
        checkIsVotingOpen(referendumVotingId, referendumVoting.isOpen());
        votingResultRepository.save(new ReferendumVotingResult(referendumVoting, acceptReferendum, user));
    }

    @Override
    public VotingResult getVotingResultForUserAndVotingId(String userId, String votingId) {
        final User user = getUser(userId);
        return votingResultRepository.findVotingResultFromUser(user.getId(), votingId);
    }

    private void checkIsVotingOpen(String referendumVotingId, boolean open) throws VotingStoppedException {
        if (!open) {
            throw new VotingStoppedException(referendumVotingId);
        }
    }

    private void checkExistingVotingResult(String userId, String majorityVotingId) {
        final VotingResult candidateVotingResultByUser = votingResultRepository.findVotingResultFromUser(userId, majorityVotingId);
        if (candidateVotingResultByUser != null) {
            throw new IllegalStateException("User has already voted for " + majorityVotingId);
        }
    }


    private User getUser(String userId) {
        final User user = userRepository.findOne(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return user;
    }


    private ReferendumVoting getReferendumVoting(String referendumVotingId) {
        final ReferendumVoting referendumVoting = referendumVotingRepository.findOne(referendumVotingId);
        if (referendumVoting == null) {
            throw new IllegalArgumentException("Could not find Referendum Voting: " + referendumVoting);
        }
        return referendumVoting;
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
