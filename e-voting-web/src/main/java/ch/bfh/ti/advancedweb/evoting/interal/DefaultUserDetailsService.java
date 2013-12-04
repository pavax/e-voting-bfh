package ch.bfh.ti.advancedweb.evoting.interal;

import ch.bfh.ti.advancedweb.evoting.UserPrincipal;
import ch.bfh.ti.advancedweb.evoting.domain.User;
import ch.bfh.ti.advancedweb.evoting.domain.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
class DefaultUserDetailsService implements UserDetailsService {

    public static final String ROLE_USER = "ROLE_USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final UserRepository userRepository;

    @Inject
    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userByUsername = userRepository.findUserByUsername(username);
        Set<String> roles = new HashSet<>();
        roles.add(ROLE_USER);
        if (userByUsername.isAdmin()) {
            roles.add(ROLE_ADMIN);
        }
        final List<GrantedAuthority> role_user = AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()]));
        return new UserPrincipal(userByUsername.getId(), username, userByUsername.getPassword(), role_user);
    }
}
