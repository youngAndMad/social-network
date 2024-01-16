package socialapp.newsservice.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> requestTemplate.header("Follow-Redirects", "true");
    }
}
