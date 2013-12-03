package ch.bfh.ti.advancedweb.voting.domain.voting;

import javax.persistence.Entity;

@Entity
public class ReferendumVoting extends Voting {

    private String question;

    public ReferendumVoting(String title, String question) {
        super(title, VotingType.REFERENDUM);
        this.question = question;
    }

    protected ReferendumVoting(){
        // FOR JPA
    }

    public String getQuestion() {
        return question;
    }
}
