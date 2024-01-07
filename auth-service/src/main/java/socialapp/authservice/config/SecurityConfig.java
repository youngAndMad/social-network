package socialapp.authservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2ClientProperties oauth2ClientProperties;

    private final static String [] WHITE_LIST = {
            "/api/v1/auth/register", "/api/v1/auth/confirm-email",  "/v3/api-docs/**", "/swagger-ui/**"
    };

    @Bean
    RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        var registeredClient = RegisteredClient
                .withId(oauth2ClientProperties.getId())
                .clientId(oauth2ClientProperties.getId())
                .clientSecret(passwordEncoder.encode(oauth2ClientProperties.getSecret()))
                .clientAuthenticationMethod(oauth2ClientProperties.getAuthenticationMethod())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri(oauth2ClientProperties.getRedirectUri())
                .scopes(scopes -> scopes.addAll(oauth2ClientProperties.getScopes()))
                .tokenSettings(
                        TokenSettings.builder()
                        .accessTokenTimeToLive(oauth2ClientProperties.getTokenSettings().getAccessTokenTimeToLive())
                        .refreshTokenTimeToLive(oauth2ClientProperties.getTokenSettings().getRefreshTokenTimeToLive())
                        .build()
                )
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().
                requestMatchers(WHITE_LIST);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
