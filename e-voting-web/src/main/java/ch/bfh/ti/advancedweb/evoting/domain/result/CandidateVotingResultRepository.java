package ch.bfh.ti.advancedweb.evoting.domain.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateVotingResultRepository extends JpaRepository<CandidateVotingResult, String> {

    @Query("select distinct count (cvr) from CandidateVotingResult cvr join cvr.votedCandidates candidate where cvr.voting.votingId = :votingId and candidate.candidateId = :candidateId ")
    Integer countVotesForCandidate(@Param("votingId") String votingId, @Param("candidateId") String candidateId);

    @Query("select distinct sum (cvr.partyListVotes) from CandidateVotingResult cvr where cvr.voting.votingId = :votingId and cvr.partyListName = :partyListName ")
    Integer countPartyVotes(@Param("votingId") String votingId, @Param("partyListName") String partyListName);

    @Query("select distinct sum (cvr.partyListVotes) from CandidateVotingResult cvr where cvr.voting.votingId = :votingId ")
    Integer countTotalPartyVotes(@Param("votingId") String votingId);

}
