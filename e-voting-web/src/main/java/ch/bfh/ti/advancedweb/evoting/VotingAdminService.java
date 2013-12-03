package ch.bfh.ti.advancedweb.evoting;

import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;

import java.util.List;
import java.util.Set;

public interface VotingAdminService {

    void stopAllVotings();

    List<Voting> getCurrentVotings();

    Set<CandidateResultData> getCandidateResults(String votingId);

    ReferendumResultData getReferendumResult(String referendumVotingId);

}
