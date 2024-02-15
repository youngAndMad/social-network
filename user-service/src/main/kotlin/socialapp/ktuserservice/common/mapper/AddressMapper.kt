package socialapp.ktuserservice.common.mapper

import org.springframework.stereotype.Component
import socialapp.ktuserservice.model.dto.AddressDto
import socialapp.ktuserservice.model.entity.Address

@Component
class AddressMapper {

    fun toModel(addressDto: AddressDto): Address {
        return Address().apply {
            country = addressDto.country
            city = addressDto.city
        }
    }

    fun update(address: Address, addressDto: AddressDto) {
        address.apply {
            country = addressDto.country
            city = addressDto.city
        }
    }
}