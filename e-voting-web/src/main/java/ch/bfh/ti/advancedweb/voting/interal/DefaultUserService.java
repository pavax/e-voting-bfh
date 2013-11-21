package ch.bfh.ti.advancedweb.voting.interal;

import ch.bfh.ti.advancedweb.voting.InvalidPasswordException;
import ch.bfh.ti.advancedweb.voting.UserNotFoundException;
import ch.bfh.ti.advancedweb.voting.UserService;
import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Inject
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUserName(final String username) throws UserNotFoundException {
        return getUserByUsername(username);
    }

    @Override
    public User checkUserNameAndPassword(final String username, final String password) throws UserNotFoundException, InvalidPasswordException {
        User user = getUserByUsername(username);
        user.checkPassword(password);
        return user;
    }

    private User getUserByUsername(String username) throws UserNotFoundException {
        final User userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername == null) {
            throw new UserNotFoundException(username);
        }
        return userByUsername;
    }
}
