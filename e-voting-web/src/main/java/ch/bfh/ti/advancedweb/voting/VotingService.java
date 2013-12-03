package ch.bfh.ti.advancedweb.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ReferendumVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VotingService {

    Map<MajorityVoting, Boolean> getCurrentMajorityVotings(String userId);

    Map<ProportionalVoting, Boolean> getCurrentProportionalVotings(String userId);

    Map<ReferendumVoting, Boolean> getCurrentReferendumVotings(String userId);

    VotingResult getVotingFromUser(String userId, Voting voting);

    void saveMajorityVote(String userId, String majorityVotingId, Set<Candidate> candidates) throws VotingStoppedException;

    void saveProportionalVote(String userId, String proportionalVotingId, List<Candidate> candidates) throws VotingStoppedException;

    void saveReferendumVote(String userId, String referendumVotingId, boolean acceptReferendum) throws VotingStoppedException;
}
