package ch.bfh.ti.advancedweb.evoting.web.votinglist;

/**
 * VOTED = User Voting is already send and saved to the Database
 * <br/>
 * SAVED = User Voting is saved in the Session as a Ballot
 * <br/>
 * STOPPED = Voting has been stopped
 * <br/>
 * NEW = User did not vote yet
 */
public enum VotingState {

    VOTED, SAVED, STOPPED, NEW
}
