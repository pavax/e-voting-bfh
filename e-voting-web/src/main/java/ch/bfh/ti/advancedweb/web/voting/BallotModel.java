package ch.bfh.ti.advancedweb.web.voting;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Scope("session")
public class BallotModel {

    private Set<MajorityBallot> majorityBallots = new LinkedHashSet<>();

    private Set<ProportionalBallot> proportionalBallots = new LinkedHashSet<>();

    private Set<ReferendumBallot> referendumBallots = new LinkedHashSet<>();


    public void clearAll() {
        this.majorityBallots.clear();
        this.proportionalBallots.clear();
        this.referendumBallots.clear();
    }

    public Set<MajorityBallot> getMajorityBallots() {
        return Collections.unmodifiableSet(this.majorityBallots);
    }

    public Set<ProportionalBallot> getProportionalBallots() {
        return Collections.unmodifiableSet(this.proportionalBallots);
    }

    public Set<ReferendumBallot> getReferendumBallots() {
        return Collections.unmodifiableSet(this.referendumBallots);
    }

    public boolean contains(String votingId) {
        return findMajorityBallot(votingId) != null
                || findProportionalBallot(votingId) != null
                || findReferendumBallot(votingId) != null;
    }

    public MajorityBallot findMajorityBallot(String votingId) {
        for (MajorityBallot majorityBallot : majorityBallots) {
            if (majorityBallot.getVotingId().equals(votingId)) {
                return majorityBallot;
            }
        }
        return null;
    }

    public ProportionalBallot findProportionalBallot(String votingId) {
        for (ProportionalBallot proportionalBallot : proportionalBallots) {
            if (proportionalBallot.getVotingId().equals(votingId)) {
                return proportionalBallot;
            }
        }
        return null;
    }

    public ReferendumBallot findReferendumBallot(String votingId) {
        for (ReferendumBallot referendumBallot : referendumBallots) {
            if (referendumBallot.getVotingId().equals(votingId)) {
                return referendumBallot;
            }
        }
        return null;
    }


    public void addMajorityBallot(MajorityBallot majorityBallot) {
        final MajorityBallot existing = findMajorityBallot(majorityBallot.getVotingId());
        if (existing != null) {
            this.majorityBallots.remove(existing);
        }
        this.majorityBallots.add(majorityBallot);
    }


    public void addProportionalBallot(ProportionalBallot proportionalBallot) {
        final ProportionalBallot existing = findProportionalBallot(proportionalBallot.getVotingId());
        if (existing != null) {
            this.proportionalBallots.remove(existing);
        }
        this.proportionalBallots.add(proportionalBallot);
    }

    public void addReferendumBallot(ReferendumBallot referendumBallot) {
        final ReferendumBallot existing = findReferendumBallot(referendumBallot.getVotingId());
        if (existing != null) {
            this.referendumBallots.remove(existing);
        }
        this.referendumBallots.add(referendumBallot);
    }

    public boolean containsAny() {
        return this.getProportionalBallots().size() != 0 || this.getMajorityBallots().size() != 0 || this.getReferendumBallots().size() != 0;
    }


}
