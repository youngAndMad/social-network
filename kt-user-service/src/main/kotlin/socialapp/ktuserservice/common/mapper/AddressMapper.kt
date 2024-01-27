package socialapp.ktuserservice.common.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import socialapp.ktuserservice.model.dto.AddressDto
import socialapp.ktuserservice.model.entity.Address

@Mapper
interface AddressMapper {

    fun toModel(addressDto: AddressDto): Address

    fun update(address: Address, @MappingTarget addressDto: AddressDto)
}
