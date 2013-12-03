package ch.bfh.ti.advancedweb.evoting.domain.voting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorityVotingRepository extends JpaRepository<MajorityVoting,String> {
}
