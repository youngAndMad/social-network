//package socialapp.chatservice.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class CorsConfig  extends CorsConfiguration implements WebMvcConfigurer{
//
//    @Value("${cors.allowCredentials}")
//    private boolean allowCredentials;
//
//    @Value("${cors.allowedOrigins}")
//    private List<String> allowedOrigins;
//
//    @Value("${cors.allowedMethods}")
//    private List<String> allowedMethods;
//
//    @Value("${cors.allowedHeaders}")
//    private List<String> allowedHeaders;
//
//    @Bean
//    public CorsFilter corsFilter() {
//        var corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(allowCredentials);
//        corsConfiguration.setAllowedOrigins(allowedOrigins);
//        corsConfiguration.setAllowedMethods(allowedMethods);
//        corsConfiguration.setAllowedHeaders(allowedHeaders);
//        corsConfiguration.setAllowCredentials(true);
//        var source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(source);
//    }
//
//}