package socialapp.ktuserservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.EnableAspectJAutoProxy
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
class UserServiceApplication {


    object TrustAllCertificates : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    val  allHostsValid = HostnameVerifier { _, _ -> true }

    init {
        try {
            val sc: SSLContext = SSLContext.getInstance("SSL")
            sc.init(null, arrayOf<TrustManager>(TrustAllCertificates), SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}