package socialapp.authservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "oauth2.client")
@Data
public class OAuth2ClientProperties {
    private String id;
    private String secret;
    private ClientAuthenticationMethod authenticationMethod;
    private List<String> grantTypes;
    private String redirectUri;
    private List<String> scopes;
    private TokenSettings tokenSettings;

    @Data
    public static class TokenSettings {
        private Duration accessTokenTimeToLive;
        private Duration refreshTokenTimeToLive;
    }
}
