package socialapp.ktuserservice.common.aspect

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.repository.UserRepository
import kotlin.math.log

@Aspect
@Component
class BeforeMeAspect (
    private val userRepository: UserRepository
){

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Before("execution(* socialapp.ktuserservice.controller.UserController.me(..))")
    fun beforeMe() {
        SecurityContextHolder.getContext().authentication?.let {
            val jwt = it.principal as Jwt
            val email = jwt.claims["email"] as String

            if (userRepository.existsByEmail(email)) {
                logger.info("User with email $email already exists")
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
            logger.info("Saving user $user")
            userRepository.save(user)
        }
    }

}
