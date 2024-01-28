package socialapp.ktuserservice.common.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers
import socialapp.ktuserservice.model.dto.AddressDto
import socialapp.ktuserservice.model.entity.Address

@Mapper(componentModel = "spring")
interface AddressMapper {

    companion object{
        val INSTANCE = Mappers.getMapper(AddressMapper::class.java)
    }

    fun toModel(addressDto: AddressDto): Address

    fun update(address: Address, @MappingTarget addressDto: AddressDto)
}
