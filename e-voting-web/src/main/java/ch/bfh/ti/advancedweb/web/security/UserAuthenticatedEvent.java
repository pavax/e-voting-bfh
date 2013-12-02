package ch.bfh.ti.advancedweb.web.security;

import ch.bfh.ti.advancedweb.voting.UserPrincipal;
import ch.bfh.ti.advancedweb.voting.domain.User;
import ch.bfh.ti.advancedweb.voting.domain.UserRepository;
import ch.bfh.ti.advancedweb.web.CurrentUserModel;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UserAuthenticatedEvent implements ApplicationListener<AuthenticationSuccessEvent> {

    private final CurrentUserModel currentUserModel;

    private final UserRepository userRepository;

    @Inject
    public UserAuthenticatedEvent(CurrentUserModel currentUserModel, UserRepository userRepository) {
        this.currentUserModel = currentUserModel;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        final UserPrincipal principal = (UserPrincipal) event.getAuthentication().getPrincipal();
        final User user = userRepository.findOne(principal.getUserId());
        currentUserModel.setUsername(user.getUsername());
        currentUserModel.setUserId(user.getId());
        currentUserModel.setRoles(AuthorityUtils.authorityListToSet(principal.getAuthorities()));
    }
}
