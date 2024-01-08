package socialapp.urlshortenerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * Configuration class for setting up Redis caching in the application.
 */
@Configuration
public class RedisCacheConfig {

    /**
     * The default time-to-live (TTL) for cached entries in minutes.
     */
    private static final int CACHE_TTL_MINUTES = 60;

    /**
     * Bean definition for configuring Redis cache with the specified TTL and serialization settings.
     *
     * @return RedisCacheConfiguration instance with the configured settings.
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CACHE_TTL_MINUTES))
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
