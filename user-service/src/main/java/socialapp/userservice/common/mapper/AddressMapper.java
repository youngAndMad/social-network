package socialapp.userservice.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import socialapp.userservice.model.dto.AddressDto;
import socialapp.userservice.model.entity.Address;

@Mapper
public interface AddressMapper {

    Address toModel(AddressDto addressDto);

    void update(@MappingTarget Address address,AddressDto addressDto);
}


