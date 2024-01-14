package socialapp.userservice.service;

import socialapp.userservice.model.dto.AddressDto;
import socialapp.userservice.model.entity.Address;

public interface AddressService {

    Address save(AddressDto addressDto);

    void update(AddressDto addressDto, Long id);
}
