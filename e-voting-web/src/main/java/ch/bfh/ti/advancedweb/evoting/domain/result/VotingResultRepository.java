package ch.bfh.ti.advancedweb.evoting.domain.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VotingResultRepository extends JpaRepository<VotingResult, String> {

    @Query("select vr from VotingResult vr where vr.voter.id = :userId and vr.voting.votingId = :votingId")
    VotingResult findVotingResultFromUser(@Param("userId") String userId, @Param("votingId") String votingId);

}
