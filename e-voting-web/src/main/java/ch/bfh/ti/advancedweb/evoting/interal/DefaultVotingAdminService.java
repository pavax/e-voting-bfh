package ch.bfh.ti.advancedweb.evoting.interal;

import ch.bfh.ti.advancedweb.evoting.*;
import ch.bfh.ti.advancedweb.evoting.domain.Candidate;
import ch.bfh.ti.advancedweb.evoting.domain.result.CandidateVotingResultRepository;
import ch.bfh.ti.advancedweb.evoting.domain.result.ReferendumVotingResultRepository;
import ch.bfh.ti.advancedweb.evoting.domain.voting.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
class DefaultVotingAdminService implements VotingAdminService {

    private final MajorityVotingRepository majorityVotingRepository;

    private final ProportionalVotingRepository proportionalVotingRepository;

    private final VotingRepository votingRepository;

    private final ReferendumVotingResultRepository referendumVotingResultRepository;

    private final CandidateVotingResultRepository candidateVotingResultRepository;

    @Inject
    public DefaultVotingAdminService(VotingRepository votingRepository, ProportionalVotingRepository proportionalVotingRepository, ReferendumVotingResultRepository referendumVotingResultRepository, CandidateVotingResultRepository candidateVotingResultRepository, MajorityVotingRepository majorityVotingRepository) {
        this.votingRepository = votingRepository;
        this.proportionalVotingRepository = proportionalVotingRepository;
        this.referendumVotingResultRepository = referendumVotingResultRepository;
        this.candidateVotingResultRepository = candidateVotingResultRepository;
        this.majorityVotingRepository = majorityVotingRepository;
    }

    @Override
    public void stopAllCurrentVotings() {
        final List<Voting> votings = votingRepository.findAll();
        for (Voting voting : votings) {
            voting.setOpen(false);
        }
    }

    @Override
    public List<Voting> getCurrentVotings() {
        return votingRepository.findAll(new Sort("createDate"));
    }

    @Override
    public Set<CandidateResultData> getMajorityVotingResultData(String votingId) {
        final MajorityVoting majorityVoting = majorityVotingRepository.findOne(votingId);
        return getMajorityResult(votingId, majorityVoting.getMajorityCandidates(), majorityVoting.getOpenPositions());
    }

    @Override
    public ProportionalVotingResultData getProportionalVotingResultData(String votingId) {
        final ProportionalVoting proportionalVoting = proportionalVotingRepository.findOne(votingId);
        if (!proportionalVoting.getVotingType().equals(VotingType.PROPORTIONAL)) {
            throw new IllegalStateException("Voting having voting type '" + proportionalVoting.getVotingType() + "' is not supported");
        }

        final int openPositions = proportionalVoting.getOpenPositions();

        final Integer countTotalPartyVotes = candidateVotingResultRepository.countTotalPartyVotes(votingId);
        final Set<PartyResultData> result = new HashSet<>();
        int quotient = 0;
        if (countTotalPartyVotes != null) {
            quotient = (countTotalPartyVotes / (openPositions + 1)) + 1;
            final Set<String> allPartyNames = proportionalVoting.getAllPartyNames();
            for (String partyName : allPartyNames) {
                final Integer partyVotes = candidateVotingResultRepository.countPartyVotes(votingId, partyName);
                if (partyVotes != null) {
                    final int partyPositionCount = Math.round(partyVotes / quotient);
                    final List<Candidate> candidatesByParty = proportionalVoting.getCandidatesByParty(partyName);
                    final Set<CandidateResultData> candidateResultDataSet = determineSelectedCandidatesForParty(partyPositionCount, votingId, new HashSet<>(candidatesByParty));
                    result.add(new PartyResultData(partyName, candidateResultDataSet, partyVotes, partyPositionCount));
                }
            }
        }
        final List<PartyResultData> sortedResult = new ArrayList<>(result);
        Collections.sort(sortedResult);
        return new ProportionalVotingResultData(new LinkedHashSet<>(sortedResult), countTotalPartyVotes, quotient);
    }

    @Override
    public ReferendumResultData getReferendumResult(String referendumVotingId) {
        final int acceptCount = referendumVotingResultRepository.countAcceptVotes(referendumVotingId);
        final int rejectCount = referendumVotingResultRepository.countRejectVotes(referendumVotingId);
        return new ReferendumResultData(acceptCount, rejectCount);
    }


    /**
     * Nachdem bekannt ist, wie viele Sitze den Parteien zukommen, gilt es, die gewählten Personen zu ernennen.
     * Gewählt sind die Personen, die am meisten Stimmen erhalten haben. Die nicht gewählten Kandidatinnen und Kandidaten
     * sind Ersatzleute in der Reihenfolge der erzielten Stimmen. Beim Rücktritt eines oder einer Gewählten rückt
     * die erste Ersatzperson nach
     *
     * @param partyPositionCount
     * @param votingId
     * @param candidatesByParty
     * @return
     */
    private Set<CandidateResultData> determineSelectedCandidatesForParty(int partyPositionCount, String votingId, HashSet<Candidate> candidatesByParty) {
        return getMajorityResult(votingId, candidatesByParty, partyPositionCount);
    }

    private Set<CandidateResultData> getMajorityResult(String votingId, Set<Candidate> candidateList, int positions) {
        Set<CandidateResultData> result = new HashSet<>();
        Map<Candidate, Integer> map = new HashMap<>();
        for (Candidate candidate : candidateList) {
            final Integer votes = candidateVotingResultRepository.countVotesForCandidate(votingId, candidate.getCandidateId());
            map.put(candidate, votes);
        }
        final Map<Candidate, Integer> sortedMap = MapUtil.sortByValue(map);
        for (Map.Entry<Candidate, Integer> candidateIntegerEntry : sortedMap.entrySet()) {
            if (candidateIntegerEntry.getValue() != null) {
                boolean elected = result.size() < positions;
                result.add(new CandidateResultData(candidateIntegerEntry.getKey(), candidateIntegerEntry.getValue(), elected));
            }
        }
        final LinkedList<CandidateResultData> list = new LinkedList<>(result);
        Collections.sort(list);
        return new LinkedHashSet<>(list);
    }

    private static class MapUtil {
        public static <K, V extends Comparable<? super V>> Map<K, V>
        sortByValue(Map<K, V> map) {
            List<Map.Entry<K, V>> list =
                    new LinkedList<Map.Entry<K, V>>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });

            Map<K, V> result = new LinkedHashMap<K, V>();
            for (Map.Entry<K, V> entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
    }
}




