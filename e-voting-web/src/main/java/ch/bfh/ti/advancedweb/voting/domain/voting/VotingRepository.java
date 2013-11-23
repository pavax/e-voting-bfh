package ch.bfh.ti.advancedweb.voting.domain.voting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<Voting,String> {
}
