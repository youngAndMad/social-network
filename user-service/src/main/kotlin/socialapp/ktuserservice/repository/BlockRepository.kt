package socialapp.ktuserservice.repository

import org.springframework.stereotype.Repository
import socialapp.ktuserservice.model.entity.Block
import socialapp.ktuserservice.repository.common.CommonRepository

@Repository
interface BlockRepository: CommonRepository<Block,Long> {

    override fun entityClass(): Class<*> = Block::class.java

    fun findAllBySenderId(senderId: Long): Set<Block>
}