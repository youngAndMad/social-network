package socialapp.ktuserservice.config.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.repository.UserRepository

class CustomAuthenticationSuccessHandler(
    private val userRepository: UserRepository
) : AuthenticationSuccessHandler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val jwt = authentication?.principal as Jwt
        val email = jwt.claims["email"] as String

        if (userRepository.existsByEmail(email)) {
            log.info("User logged in: $email")
            return
        }

        val preferredUsername = jwt.claims["preferred_username"] as String
        val givenName = jwt.claims["given_name"] as String
        val familyName = jwt.claims["family_name"] as String
        val user = User().apply {
            this.email = email
            this.preferredUsername = preferredUsername
            this.givenName = givenName
            this.familyName = familyName
        }
        log.info("User logged in: $user.email and registered")
        userRepository.save(user)
    }
}