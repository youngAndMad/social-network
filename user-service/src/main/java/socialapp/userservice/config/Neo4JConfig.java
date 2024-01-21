package socialapp.userservice.config;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Neo4JConfig {

    @Bean
    Configuration cypherDslConfiguration() {
        return Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }

}
