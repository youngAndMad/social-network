package socialapp.authservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.authservice.common.exception.EmailNotVerifiedException;
import socialapp.authservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {

        var optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            log.warn("invalid user credentials email = {}", email);
            throw new UsernameNotFoundException("invalid user credentials email %s".formatted(email));
        }

        var user = optionalUser.get();

        if (!user.getEmailVerified()){
            throw new EmailNotVerifiedException(user.getEmail());
        }

        log.debug("successfully authenticated user email = {}, id = {}", user.getEmail(), user.getId());

        return new AppUserDetails(user);
    }

}
