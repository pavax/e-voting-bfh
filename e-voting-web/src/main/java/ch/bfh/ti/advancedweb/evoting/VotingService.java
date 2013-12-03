package ch.bfh.ti.advancedweb.evoting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ReferendumVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VotingService {

    Map<MajorityVoting, Boolean> getCurrentMajorityVotings(String userId);

    Map<ProportionalVoting, Boolean> getCurrentProportionalVotings(String userId);

    Map<ReferendumVoting, Boolean> getCurrentReferendumVotings(String userId);

    void saveMajorityVote(String userId, String majorityVotingId, Set<Candidate> candidates) throws VotingStoppedException;

    void saveProportionalVote(String userId, String proportionalVotingId, List<Candidate> candidates) throws VotingStoppedException;

    void saveReferendumVote(String userId, String referendumVotingId, boolean acceptReferendum) throws VotingStoppedException;

    VotingResult getVotingFromUser(String userId, Voting voting);
}
