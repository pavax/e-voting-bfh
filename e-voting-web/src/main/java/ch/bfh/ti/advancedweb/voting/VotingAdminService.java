package ch.bfh.ti.advancedweb.voting;

import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import java.util.List;
import java.util.Set;

public interface VotingAdminService {

    void stopAllVotings();

    List<Voting> getCurrentVotings();

    Set<CandidateResult> getCandidateResults(String votingId);


}
