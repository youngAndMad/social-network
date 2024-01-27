package socialapp.chatservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                                            "/swagger-ui.html",
                                    "/**"
                                    ).permitAll()
                                    .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                                    .anyRequest().authenticated();
                        }
                )
                .cors().disable().csrf().disable()
                .oauth2ResourceServer().jwt();

        http.headers().frameOptions().disable()
                .httpStrictTransportSecurity().disable();


        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(oidcIssuerLocation);
    }
}
