package socialapp.ktuserservice.repository

import org.springframework.stereotype.Repository
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.repository.common.CommonRepository

@Repository
interface AddressRepository: CommonRepository<Address, Long> {

    override fun entityClass(): Class<*> = Address::class.java
}