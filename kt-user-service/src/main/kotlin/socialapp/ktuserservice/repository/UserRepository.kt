package socialapp.ktuserservice.repository

import org.springframework.stereotype.Repository
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.repository.common.CommonRepository

@Repository
interface UserRepository : CommonRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?

    override fun entityClass(): Class<*> = User::class.java
}

