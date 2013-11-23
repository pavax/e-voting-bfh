package ch.bfh.ti.advancedweb.voting.domain.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateVotingResultRepository extends JpaRepository<CandidateVotingResult, String> {

    @Query("select distinct count (cvr) from CandidateVotingResult cvr join cvr.votedCandidates candidate where cvr.voting.votingId = :votingId and candidate.candidateId = :candidateId ")
    int countVotesForCandidate(@Param("votingId") String votingId, @Param("candidateId") String candidateId);

}
