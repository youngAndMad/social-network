package socialapp.newsservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String oidcIssuerLocation;

    @Bean
    SecurityFilterChain oauth2FilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(
                                            "/api-docs/**",
                                            "/swagger-ui/**",
                                            "/swagger-ui.html"
                                    ).permitAll()
                                    .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                                    .anyRequest().authenticated();
                        }
                )
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .oauth2ResourceServer().jwt();
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(oidcIssuerLocation);
    }
}
