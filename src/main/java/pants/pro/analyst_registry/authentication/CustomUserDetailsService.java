package pants.pro.analyst_registry.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pants.pro.analyst_registry.repository.UserRepository;

@Service
@RequiredArgsConstructor
/**
 * Loads application users from persistence for Spring Security authentication.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads a user by username for Spring Security.
     * @param username username entered at login.
     * @return user details used by Spring Security.
     * @throws UsernameNotFoundException if username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username=" + username + " not found!"));
    }
}
