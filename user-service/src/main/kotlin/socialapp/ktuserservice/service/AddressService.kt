package socialapp.ktuserservice.service

import socialapp.ktuserservice.model.dto.AddressDto
import socialapp.ktuserservice.model.entity.Address

interface AddressService {

    fun save(addressDto: AddressDto): Address

    fun update(addressDto: AddressDto, address: Address)
}
