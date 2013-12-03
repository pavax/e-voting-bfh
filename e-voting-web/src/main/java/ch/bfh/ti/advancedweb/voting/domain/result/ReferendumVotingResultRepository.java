package ch.bfh.ti.advancedweb.voting.domain.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReferendumVotingResultRepository extends JpaRepository<ReferendumVotingResult, String> {

    @Query("select distinct count (rvr) from ReferendumVotingResult rvr where rvr.voting.votingId = :votingId and rvr.accept = true")
    int countAcceptVotes(@Param("votingId") String votingId);

    @Query("select distinct count (rvr) from ReferendumVotingResult rvr where rvr.voting.votingId = :votingId and rvr.accept = false")
    int countRejectVotes(@Param("votingId") String votingId);
}
