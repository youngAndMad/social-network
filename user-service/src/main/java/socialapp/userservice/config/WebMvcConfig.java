package socialapp.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import socialapp.userservice.common.interceptor.LoggingInterceptor;

import static socialapp.userservice.common.AppConstants.LOGGING_PATH_PATTERN;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns(LOGGING_PATH_PATTERN);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
