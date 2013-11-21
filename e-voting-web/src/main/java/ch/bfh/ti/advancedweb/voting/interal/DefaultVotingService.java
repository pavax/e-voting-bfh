package ch.bfh.ti.advancedweb.voting.interal;

import ch.bfh.ti.advancedweb.voting.VotingService;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.UserRepository;
import ch.bfh.ti.advancedweb.voting.domain.result.CandidateVotingResult;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResultRepository;
import ch.bfh.ti.advancedweb.voting.domain.voting.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DefaultVotingService implements VotingService {

    private final MajorzVotingRepository majorzVotingRepository;

    private final ProporzVotingRepository proporzVotingRepository;

    private final VotingResultRepository votingResultRepository;

    private final UserRepository userRepository;

    @Inject
    public DefaultVotingService(MajorzVotingRepository majorzVotingRepository, ProporzVotingRepository proporzVotingRepository, VotingResultRepository votingResultRepository, UserRepository userRepository) {
        this.majorzVotingRepository = majorzVotingRepository;
        this.proporzVotingRepository = proporzVotingRepository;
        this.votingResultRepository = votingResultRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Map<MajorzVoting, Boolean> getCurrentMajorzVotings(String userId) {
        Map<MajorzVoting, Boolean> resultMap = new HashMap<>();
        final List<MajorzVoting> majorzVotings = majorzVotingRepository.findAll();
        for (MajorzVoting majorzVoting : majorzVotings) {
            final VotingResult resultByUser = votingResultRepository.findCandidateVotingResultByUser(userId, majorzVoting.getVotingId());
            resultMap.put(majorzVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public Map<ProporzVoting, Boolean> getCurrentProporzVotings(String userId) {
        Map<ProporzVoting, Boolean> resultMap = new HashMap<>();
        final List<ProporzVoting> proporzVotings = proporzVotingRepository.findAll();
        for (ProporzVoting proporzVoting : proporzVotings) {
            final VotingResult resultByUser = votingResultRepository.findCandidateVotingResultByUser(userId, proporzVoting.getVotingId());
            resultMap.put(proporzVoting, resultByUser != null);
        }
        return resultMap;
    }

    @Override
    public void vote(String userId, Voting voting, List<Candidate> candidates) {
        final User user = getUser(userId);
        final CandidateVotingResult candidateVotingResult = new CandidateVotingResult(voting, candidates, user);
        votingResultRepository.save(candidateVotingResult);
    }


    @Override
    public VotingResult getVotingResultForUser(String userId, Voting voting) {
        final User user = getUser(userId);
        final VotingResult votingResultByUser = votingResultRepository.findCandidateVotingResultByUser(user.getId(), voting.getVotingId());
        return votingResultByUser;
    }

    private User getUser(String userId) {
        final User user = userRepository.findOne(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return user;
    }
}
