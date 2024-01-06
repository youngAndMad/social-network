package socialapp.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import socialapp.authservice.repository.UserRepository;

import javax.security.auth.login.CredentialExpiredException;

@Configuration
public class SecurityConfig {

    @Bean
    RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder){
        var registeredClient = RegisteredClient.withId("demo")
                .clientId("demo")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/reg-client")
                .scope("user.read")
                .scope("user.write")
                .scope("openid")
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

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
