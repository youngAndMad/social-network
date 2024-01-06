package socialapp.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import socialapp.authservice.UserRepository;

import javax.security.auth.login.CredentialExpiredException;

@Configuration
public class SecurityConfig {

//  @Bean
//  UserDetailsService inMemoryUserDetailsManager() {
//    var userBuilder = User.builder();
//    UserDetails himanshu = userBuilder.username("himanshu").
//            password("{bcrypt}$2a$10$Smgk4iacoRi6vHRxFUf47OIJferGMqKSG37yXRXHcVj3HkA7LU8n2").roles("USER", "ADMIN").build();
//    return new InMemoryUserDetailsManager(himanshu);
//  }
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/registration");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> {
            try {
                return userRepository.findByEmail(email)
                        .orElseThrow(CredentialExpiredException::new);
            } catch (CredentialExpiredException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
