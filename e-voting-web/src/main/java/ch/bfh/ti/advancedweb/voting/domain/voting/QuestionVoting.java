package ch.bfh.ti.advancedweb.voting.domain.voting;

public class QuestionVoting extends Voting {

    private String question;

    public QuestionVoting(String title) {
        super(title, VotingType.QUESTION);
    }

    protected QuestionVoting(){
        // FOR JPA
    }

    public String getQuestion() {
        return question;
    }
}
