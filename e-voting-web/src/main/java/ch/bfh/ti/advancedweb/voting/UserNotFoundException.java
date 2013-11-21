package ch.bfh.ti.advancedweb.voting;

public class UserNotFoundException extends Exception {
    private final String username;

    public UserNotFoundException(String username) {

        this.username = username;
    }
}
