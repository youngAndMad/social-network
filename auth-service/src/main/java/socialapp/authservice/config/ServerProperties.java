package socialapp.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
@Getter
@Setter
public class ServerProperties {
    private Integer port;
    private String host;
    private String scheme;

    public String getPath() {
        return "%s://%s:%d".formatted(scheme, host, port);
    }
}
