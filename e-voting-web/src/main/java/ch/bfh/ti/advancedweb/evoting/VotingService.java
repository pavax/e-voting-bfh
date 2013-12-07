package ch.bfh.ti.advancedweb.evoting;

import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.result.VotingResult;
import ch.bfh.ti.advancedweb.evoting.domain.voting.MajorityVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ProportionalVoting;
import ch.bfh.ti.advancedweb.evoting.domain.voting.ReferendumVoting;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service for a User in order to retrieve current Votings (ReferendumVoting,MajorityVoting or ProportionalVoting)
 * and save votes for a User
 */
@Secured("ROLE_USER")
public interface VotingService {

    /**
     * Retrieve current Majority-Votings as a Map where the Key is a Mojority-Voting
     * and the Value is a Boolean whether the given User has already voted for that Mojority-Vote or not
     *
     * @param userId the userId in order to decide if the User already voted or not
     * @return Map containing the MajorityVoting and a Boolean-Flag whether the User already voted
     */
    Map<MajorityVoting, Boolean> getCurrentMajorityVotings(String userId);

    /**
     * Retrieve current ProportionalVoting-Votings as a Map where the Key is a ProportionalVoting-Voting
     * and the Value is a Boolean whether the given User has already voted for that ProportionalVoting-Vote or not
     *
     * @param userId the userId in order to decide if the User already voted or not
     * @return Map containing the ProportionalVoting and a Boolean-Flag whether the User already voted
     */
    Map<ProportionalVoting, Boolean> getCurrentProportionalVotings(String userId);

    /**
     * Retrieve current ReferendumVoting-Votings as a Map where the Key is a ReferendumVoting-Voting
     * and the Value is a Boolean whether the given User has already voted for that ReferendumVoting-Vote or not
     *
     * @param userId the userId in order to decide if the User already voted or not
     * @return Map containing the ReferendumVoting and a Boolean-Flag whether the User already voted
     */
    Map<ReferendumVoting, Boolean> getCurrentReferendumVotings(String userId);

    /**
     * Saves a User's Vote for the given Mojority-Voting by providing the Set of Candidate the user wants to vote for
     *
     * @param userId           saves the vote for the given userId
     * @param majorityVotingId saves the vote the given majority voting
     * @param candidates       give the vote to given Set of Candidate
     * @throws VotingStoppedException
     */
    void saveMajorityVote(String userId, String majorityVotingId, Set<Candidate> candidates) throws VotingStoppedException;

    /**
     * Saves a User's Vote for the given Proportional-Voting by providing the List of Candidate the user wants to vote for
     * <p/>
     * Make sure that a Candidate does not occur more than two-times within the List of Candidate
     * otherwise an IllegalArgumentException is thrown
     *
     * @param userId               saves the vote for the given userId
     * @param proportionalVotingId saves the vote the given  proportional voting
     * @param candidates           give the vote to given Set of Candidate
     * @param partyListName        optionally the name of the party
     * @throws VotingStoppedException
     */
    void saveProportionalVote(String userId, String proportionalVotingId, String partyListName, List<Candidate> candidates) throws VotingStoppedException;

    /**
     * Saves a User's Vote for the given Referendum-Voting
     *
     * @param userId             saves the vote for the given userId
     * @param referendumVotingId saves the vote the given  referendum voting
     * @param acceptReferendum   Boolean whether the user accepts the referendum or not
     * @throws VotingStoppedException
     */
    void saveReferendumVote(String userId, String referendumVotingId, boolean acceptReferendum) throws VotingStoppedException;

    /**
     * Retrieve the saved User's Vote for the given Voting
     *
     * @param userId   retrieve the saved voting for the given User and Voting
     * @param votingId the votingId to retrieve the VotingResult
     * @return the VotingResult for the given User and Voting
     */
    VotingResult getVotingResultForUserAndVotingId(String userId, String votingId);
}
