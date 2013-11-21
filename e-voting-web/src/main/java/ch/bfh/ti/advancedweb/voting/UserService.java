package ch.bfh.ti.advancedweb.voting;

import ch.bfh.ti.advancedweb.voting.domain.User;

public interface UserService  {

    User loadUserByUserName(String username) throws UserNotFoundException;

    User checkUserNameAndPassword(String username, String password) throws UserNotFoundException, InvalidPasswordException;
}
