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
     * Retrieves all Current Votes sorted by their creation date
     *
     * @return List of current Votes
     */
    List<Voting> getCurrentVotings();

    /**
     * Retrieves the Set of CandidateResultData having the sum of the votes for each candidate of the given votingId
     * The Voting must match ether to an MajorityVotnng
     *
     * @param votingId the given votingId (matching to an Majority-Voting) for the result to retrieve
     * @return the Set of CandidateResultData
     */
    Set<CandidateResultData> getMajorityVotingResultData(String votingId);

    /**
     * Calculates the proportional voting result (withouth the Restverteilung) for the given votingId and returns the DTO
     *
     * @param votingId the given votingId (matching to an Proportional-Voting) for the result to retrieve
     * @return a ProportionalVotingResultData containing the total-party counts and the party-counts and elected candidates of each party
     */
    ProportionalVotingResultData getProportionalVotingResultData(String votingId);


    /**
     * Retrieves the sum of accepted and rejected votes for the given referendum Voting and returns the DTO
     *
     * @param referendumVotingId the Id of the Referendum to retrieve
     * @return the ReferendumResultData containing the sum of accepted and rejected votes for the given Referendum
     */
    ReferendumResultData getReferendumResult(String referendumVotingId);

}
