package socialapp.chatservice.config;

import io.lettuce.core.ReadFrom;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisConfig {

    private RedisInstance master;
    private List<RedisInstance> slaves;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        var clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();
        var staticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(master.getHost(), master.getPort());
        slaves.forEach(slave -> staticMasterReplicaConfiguration.addNode(slave.getHost(), slave.getPort()));
        return new LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig);
    }

    @Bean
     <K, V> RedisTemplate<K, V> reactiveStringRedisTemplate(
            @Qualifier("redisConnectionFactory") RedisConnectionFactory fct
    ) {
        var template = new RedisTemplate<K,V>();

        template.setConnectionFactory(fct);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

    @Setter
    @Getter
    private static class RedisInstance {
        private String host;
        private int port;
    }
}
