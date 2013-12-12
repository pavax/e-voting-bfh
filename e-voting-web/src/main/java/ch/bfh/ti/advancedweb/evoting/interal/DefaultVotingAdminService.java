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
        checkVotingExists(majorityVoting);
        checkVotingType(VotingType.MAJORITY, majorityVoting.getVotingType());
        return getMajorityResult(votingId, majorityVoting.getMajorityCandidates(), majorityVoting.getOpenPositions());
    }

    @Override
    public ProportionalVotingResultData getProportionalVotingResultData(String votingId) {
        final ProportionalVoting proportionalVoting = proportionalVotingRepository.findOne(votingId);
        checkVotingExists(proportionalVoting);
        checkVotingType(VotingType.PROPORTIONAL, proportionalVoting.getVotingType());
        final int openPositions = proportionalVoting.getOpenPositions();
        final Integer countTotalPartyVotes = candidateVotingResultRepository.countTotalPartyVotes(votingId);
        final Set<PartyResultData> result = calculateResultForEachParty(proportionalVoting, openPositions, countTotalPartyVotes);
        return new ProportionalVotingResultData(sortPartyResultData(result), countTotalPartyVotes);
    }

    @Override
    public ReferendumResultData getReferendumResult(String referendumVotingId) {
        final Voting referendumVoting = votingRepository.findOne(referendumVotingId);
        checkVotingExists(referendumVoting);
        checkVotingType(VotingType.REFERENDUM, referendumVoting.getVotingType());
        final int acceptCount = referendumVotingResultRepository.countAcceptVotes(referendumVotingId);
        final int rejectCount = referendumVotingResultRepository.countRejectVotes(referendumVotingId);
        return new ReferendumResultData(acceptCount, rejectCount);
    }

    private void checkVotingType(VotingType votingType, VotingType votingType1) {
        if (!votingType1.equals(votingType)) {
            throw new IllegalStateException("Voting having voting type '" + votingType + "' is not supported");
        }
    }

    private void checkVotingExists(Voting voting) {
        if (voting == null) {
            throw new IllegalArgumentException("Voting not found");
        }
    }

    /**
     * Jede Partei erhält so viele Sitze, als die Verhältniszahl in ihrer Parteistimmenzahl enthalten ist.
     *
     * @param proportionalVoting the given proportionalVoting
     * @param quotient           given Verhältniszahl
     * @param partyName          the given partyName to retrieve the result
     * @return PartyResultData contains the info about the elected candidates for the given party
     */
    private PartyResultData calculatePartyResultData(ProportionalVoting proportionalVoting, int quotient, String partyName) {
        final Integer partyVotes = candidateVotingResultRepository.countPartyVotes(proportionalVoting.getVotingId(), partyName);
        if (partyVotes != null) {
            final int partyPositionCount = Math.round(partyVotes / quotient);
            final List<Candidate> candidatesByParty = proportionalVoting.getCandidatesByParty(partyName);
            final Set<CandidateResultData> candidateResultDataSet = determineSelectedCandidatesForParty(partyPositionCount, proportionalVoting.getVotingId(), new HashSet<>(candidatesByParty));
            return new PartyResultData(partyName, candidateResultDataSet, partyVotes, partyPositionCount);
        }
        return null;
    }

    /**
     * Nachdem bekannt ist, wie viele Sitze den Parteien zukommen, gilt es, die gewählten Personen zu ernennen.
     * Gewählt sind die Personen, die am meisten Stimmen erhalten haben. Die nicht gewählten Kandidatinnen und Kandidaten
     * sind Ersatzleute in der Reihenfolge der erzielten Stimmen. Beim Rücktritt eines oder einer Gewählten rückt
     * die erste Ersatzperson nach
     *
     * @param partyPositionCount calculated seats for an party
     * @param votingId           the votingId of the voting to count the result
     * @param candidatesByParty  all candidates of a specific party
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

    private Set<PartyResultData> sortPartyResultData(Set<PartyResultData> result) {
        final List<PartyResultData> sortedResult = new ArrayList<>(result);
        Collections.sort(sortedResult);
        return new LinkedHashSet<>(sortedResult);
    }


    private Set<PartyResultData> calculateResultForEachParty(ProportionalVoting proportionalVoting, int openPositions, Integer countTotalPartyVotes) {
        final Set<PartyResultData> result = new HashSet<>();
        if (countTotalPartyVotes != null) {
            // Bestimmung der Verhältniszahl (auch Quotient genannt). Gesamtzahl der Parteistimmen geteilt durch die um eins erhöhte Zahl der Sitze.
            int quotient = (countTotalPartyVotes / (openPositions + 1)) + 1;
            for (String partyName : proportionalVoting.getAllPartyNames()) {
                final PartyResultData partyResultData = calculatePartyResultData(proportionalVoting, quotient, partyName);
                if (partyResultData != null) {
                    result.add(partyResultData);
                }
            }
        }
        return result;
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




