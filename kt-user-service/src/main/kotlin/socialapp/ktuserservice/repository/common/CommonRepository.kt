package socialapp.ktuserservice.repository.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import socialapp.ktuserservice.common.exception.EntityNotFoundException
import java.io.Serializable

@NoRepositoryBean
interface CommonRepository<E, P : Serializable> : JpaRepository<E, P> {

    fun findByID(id: P): E {
        return findById(id)
            .orElseThrow { EntityNotFoundException(entityClass(), id) }
    }

    fun entityClass(): Class<*>
}