package ch.bfh.ti.advancedweb.voting;

import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProporzVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import java.util.List;
import java.util.Map;

public interface VotingService {

    Map<MajorzVoting, Boolean> getCurrentMajorzVotings(String userId);

    Map<ProporzVoting, Boolean> getCurrentProporzVotings(String userId);

    void vote(String userId, Voting voting, List<Candidate> candidates);

    VotingResult getVotingResultForUser(String userId, Voting voting);
}
