package ch.bfh.ti.advancedweb.evoting.interal;

import ch.bfh.ti.advancedweb.evoting.ApplicationTestDataLoader;
import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.UserRepository;
import ch.bfh.ti.advancedweb.evoting.domain.voting.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
class DefaultApplicationTestDataLoader implements ApplicationTestDataLoader, ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final MajorityVotingRepository majorityVotingRepository;

    private final ProportionalVotingRepository proportionalVotingRepository;

    private final ReferendumVotingRepository referendumVotingRepository;

    private boolean initialised = false;

    private TransactionTemplate transactionTemplate;

    @Inject
    public DefaultApplicationTestDataLoader(UserRepository userRepository, MajorityVotingRepository majorityVotingRepository, ProportionalVotingRepository proportionalVotingRepository, PlatformTransactionManager platformTransactionManager, ReferendumVotingRepository referendumVotingRepository) {
        this.userRepository = userRepository;
        this.majorityVotingRepository = majorityVotingRepository;
        this.proportionalVotingRepository = proportionalVotingRepository;
        this.referendumVotingRepository = referendumVotingRepository;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public void loadTestData() {
        setupUserTestData();
        setupMajorzTestData();
        setupMajorzTestData2();
        setupProporzTestData();
        setupReferendumTestData();
    }

    private void setupUserTestData() {
        userRepository.save(new User("max", "secret", false));
        userRepository.save(new User("moritz", "secret", false));
        userRepository.save(new User("susi", "secret", false));
        userRepository.save(new User("gabi", "secret", false));
        userRepository.save(new User("hans", "secret", false));
        userRepository.save(new User("admin", "secret", true));
    }

    private void setupReferendumTestData() {
        referendumVotingRepository.save(new ReferendumVoting("Familieninitiative", "Annahme der Familieninitiative?"));
    }

    private void setupProporzTestData() {
        List<Candidate> candidates = new ArrayList<>();
        setupCandidate(candidates, "SP", 8);
        setupCandidate(candidates, "SVP", 8);
        setupCandidate(candidates, "CVP", 8);
        setupCandidate(candidates, "Grünen", 8);
        setupCandidate(candidates, "GLP", 8);
        setupCandidate(candidates, "BDP", 8);
        setupCandidate(candidates, "FDP", 8);
        proportionalVotingRepository.save(new ProportionalVoting("Proporzwahl Titel 1", 8, candidates));
    }

    private void setupCandidate(List<Candidate> candidates, String partyName, int number) {
        for (int i = 0; i < number; i++) {
            candidates.add(new Candidate("Tester-" + i, "Tester" + i, partyName));
        }
    }

    private void setupMajorzTestData() {
        Set<Candidate> candidates = new HashSet<>();
        candidates.add(new Candidate("Hans", "Fehr", "SP"));
        candidates.add(new Candidate("Peter", "Stückli", "SP"));
        candidates.add(new Candidate("Monika", "Tester", "SVP"));
        candidates.add(new Candidate("Paul", "Neuschwander", "SVP"));
        candidates.add(new Candidate("Beat", "Neuenegg", "CVP"));
        candidates.add(new Candidate("Sybille", "Küntzli", "FDP"));
        candidates.add(new Candidate("Gerhard", "Stäubli", "Grünen"));
        majorityVotingRepository.save(new MajorityVoting("Majorzwahl Titel 1", 2, candidates));
    }

    private void setupMajorzTestData2() {
        Set<Candidate> candidates = new HashSet<>();
        candidates.add(new Candidate("Kevin", "Cueni", "SP"));
        candidates.add(new Candidate("Lisa", "Simpson", "SP"));
        candidates.add(new Candidate("Beat", "Streng", "SVP"));
        candidates.add(new Candidate("Marco", "Gschwind", "SVP"));
        majorityVotingRepository.save(new MajorityVoting("Majorzwahl Titel 2", 2, candidates));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initialised) {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    loadTestData();
                }
            });
        }
        initialised = true;
    }
}
