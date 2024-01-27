package socialapp.ktuserservice.config

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.slf4j.LoggerFactory
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import kotlin.properties.Delegates

@Configuration
class ImperativeElasticsearchConfig : ElasticsearchConfiguration() {

    private companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${spring.data.elasticsearch.url}")
    private lateinit var url: String

    @Value("\${spring.data.elasticsearch.connection-timeout}")
    private var connectionTimeout:Long = 1000

    @Value("\${spring.data.elasticsearch.should-create-index}")
    private lateinit var shouldCreateIndexClasses: Array<Class<*>>

    override fun clientConfiguration(): ClientConfiguration = ClientConfiguration.builder()
        .connectedTo(url)
        .withConnectTimeout(connectionTimeout)
        .build()

    @Bean
    fun applicationRunner(elastic: ElasticsearchOperations): ApplicationRunner {
        return ApplicationRunner { _ ->
            for (shouldCreateIndexClass in shouldCreateIndexClasses) {
                if (!elastic.indexOps(shouldCreateIndexClass).exists()) {
                    log.info("index for class {} not found", shouldCreateIndexClass.simpleName)
                    val created = elastic.indexOps(shouldCreateIndexClass).create()
                    log.info("create index attempt for {}, created = {}", shouldCreateIndexClass.simpleName, created)
                } else {
                    log.info("index for class {} found", shouldCreateIndexClass.simpleName)
                }
            }
        }
    }
}