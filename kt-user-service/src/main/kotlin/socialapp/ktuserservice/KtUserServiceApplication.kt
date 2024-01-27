package socialapp.ktuserservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class KtUserServiceApplication

fun main(args: Array<String>) {
    runApplication<KtUserServiceApplication>(*args)
}
