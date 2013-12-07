package ch.bfh.ti.advancedweb.evoting;

import ch.bfh.ti.advancedweb.evoting.domain.voting.Voting;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Set;


/**
 * Voting Operations for an User with Admin Privileges
 */
@Secured("ROLE_ADMIN")
public interface VotingAdminService {

    /**
     * Stopps all current Vote so that no User can give their vote anymore
     */
    void stopAllCurrentVotings();

    /**
     * Retrieves all Current Votes
     *
     * @return List of current Votes
     */
    List<Voting> getCurrentVotings();

    /**
     * Retrieves the Set of CandidateResultData having the sum of the votes for each candidate of the given votingId
     * The Voting must match ether to an MajorityVoting or an ProportionalVoting
     *
     * @param votingId the given votingId for the result to retrieve
     * @return the Set of CandidateResultData
     */
    Set<CandidateResultData> getMajorityVotingResultData(String votingId);


     ProportionalVotingResultData getProportionalVotingResultData(String votingId);


    /**
     * Retrieves the sum of accepted and rejected votes for the given referendum Voting
     *
     * @param referendumVotingId the Id of the Referendum to retrieve
     * @return the ReferendumResultData containing the sum of accepted and rejected votes for the given Referendum
     */
    ReferendumResultData getReferendumResult(String referendumVotingId);

}
