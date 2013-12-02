package ch.bfh.ti.advancedweb.voting.interal;

import ch.bfh.ti.advancedweb.voting.domain.ApplicationTestDataLoader;
import ch.bfh.ti.advancedweb.voting.domain.Candidate;
import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.UserRepository;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.MajorzVotingRepository;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProporzVoting;
import ch.bfh.ti.advancedweb.voting.domain.voting.ProporzVotingRepository;
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
public class DefaultApplicationTestDataLoader implements ApplicationTestDataLoader, ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final MajorzVotingRepository majorzVotingRepository;

    private final ProporzVotingRepository proporzVotingRepository;

    private boolean initialised = false;

    private TransactionTemplate transactionTemplate;

    @Inject
    public DefaultApplicationTestDataLoader(UserRepository userRepository, MajorzVotingRepository majorzVotingRepository, ProporzVotingRepository proporzVotingRepository, PlatformTransactionManager platformTransactionManager) {
        this.userRepository = userRepository;
        this.majorzVotingRepository = majorzVotingRepository;
        this.proporzVotingRepository = proporzVotingRepository;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public void loadTestData() {
        userRepository.save(new User("max", "secret"));
        userRepository.save(new User("moritz", "secret"));
        userRepository.save(new User("admin", "secret"));
        setUpMajorzTestData();
        setUpMajorzTestData2();
        setupProporzTestData();
    }

    private void setupProporzTestData() {
        List<Candidate> candidates = new ArrayList<>();
        setupCandidate(candidates, "SP", 8);
        setupCandidate(candidates, "SVP", 8);
        setupCandidate(candidates, "CVP", 8);
        setupCandidate(candidates, "Grünen", 8);
        proporzVotingRepository.save(new ProporzVoting("Proporzwahl Titel 1", 8, candidates));
    }

    private void setupCandidate(List<Candidate> candidates, String partyName, int number) {
        for (int i = 0; i < number; i++) {
            candidates.add(new Candidate("Tester-" + i, "Tester" + i, partyName));
        }
    }

    private void setUpMajorzTestData() {
        Set<Candidate> candidates = new HashSet<>();
        candidates.add(new Candidate("Hans", "Fehr", "SP"));
        candidates.add(new Candidate("Peter", "Stückli", "SP"));
        candidates.add(new Candidate("Monika", "Tester", "SVP"));
        candidates.add(new Candidate("Paul", "Neuschwander", "SVP"));
        candidates.add(new Candidate("Beat", "Neuenegg", "CVP"));
        candidates.add(new Candidate("Sybille", "Küntzli", "FDP"));
        candidates.add(new Candidate("Gerhard", "Stäubli", "Grünen"));
        majorzVotingRepository.save(new MajorzVoting("Majorzwahl Titel 1", 2, candidates));
    }

    private void setUpMajorzTestData2() {
        Set<Candidate> candidates = new HashSet<>();
        candidates.add(new Candidate("Kevin", "Cueni", "SP"));
        candidates.add(new Candidate("Lisa", "Simpson", "SP"));
        candidates.add(new Candidate("Beat", "Streng", "SVP"));
        candidates.add(new Candidate("Marco", "Gschwind", "SVP"));
        majorzVotingRepository.save(new MajorzVoting("Majorzwahl Titel 2", 2, candidates));
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
