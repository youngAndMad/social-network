package socialapp.mailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * Configuration class for FreeMarker template engine integration.
 * Configures FreeMarker template loader path.
 *
 * @author Daneker
 */
@Configuration
public class FreeMarkerConfig {

    @Value("${spring.freemarker.template-loader-path}")
    private String ftlLoaderPath;

    /**
     * Configures a FreeMarkerConfigurationFactoryBean with the specified template loader path.
     *
     * @return FreeMarkerConfigurationFactoryBean instance
     */
    @Bean
    @Primary
    FreeMarkerConfigurationFactoryBean ftl() {
        var bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath(ftlLoaderPath);
        return bean;
    }

}
