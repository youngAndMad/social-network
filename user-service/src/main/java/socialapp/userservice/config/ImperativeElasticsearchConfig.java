package socialapp.userservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import socialapp.userservice.model.entity.User;

@Configuration
@Slf4j
public class ImperativeElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.data.elasticsearch.url}")
    private String url;

    @Value("${spring.data.elasticsearch.connection-timeout}")
    private Long connectionTimeout;
    @Value("${spring.data.elasticsearch.should-create-index}")
    private Class<?>[] shouldCreateIndexClasses;


    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url)
                .withConnectTimeout(connectionTimeout)
                .build();
    }

    @Bean
    ApplicationRunner applicationRunner(ElasticsearchOperations elastic) {
        return args -> {
            for (var shouldCreateIndexClass : shouldCreateIndexClasses) {
                if (!elastic.indexOps(shouldCreateIndexClass).exists()) {
                    log.info("index for class {} not found", shouldCreateIndexClass.getSimpleName());
                    var created = elastic.indexOps(shouldCreateIndexClass).create();
                    log.info("create index attempt for {}, created = {}", shouldCreateIndexClass.getSimpleName(), created);
                }else {
                    log.info("index for class {} found", shouldCreateIndexClass.getSimpleName());
                }
            }
        };
    }


}


