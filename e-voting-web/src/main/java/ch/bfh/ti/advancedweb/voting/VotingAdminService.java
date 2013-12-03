package ch.bfh.ti.advancedweb.voting;

import ch.bfh.ti.advancedweb.voting.domain.voting.Voting;

import java.util.List;
import java.util.Set;

public interface VotingAdminService {

    void stopAllVotings();

    List<Voting> getCurrentVotings();

    Set<CandidateResultData> getCandidateResults(String votingId);

    ReferendumResultData getReferendumResult(String referendumVotingId);

}
