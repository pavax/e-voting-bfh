package ch.bfh.ti.advancedweb.evoting.domain.voting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProportionalVotingRepository extends JpaRepository<ProportionalVoting,String> {
}
