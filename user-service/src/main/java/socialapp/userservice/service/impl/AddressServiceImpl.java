package socialapp.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialapp.userservice.common.exception.EntityNotFoundException;
import socialapp.userservice.common.mapper.AddressMapper;
import socialapp.userservice.model.dto.AddressDto;
import socialapp.userservice.model.entity.Address;
import socialapp.userservice.repository.AddressRepository;
import socialapp.userservice.service.AddressService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public Address save(AddressDto addressDto) {
        return addressRepository.save(addressMapper.toModel(addressDto));
    }

    @Override
    public void update(AddressDto addressDto, Long id) {
        var address = findById(id);

        addressMapper.update(address, addressDto);

        addressRepository.save(address);
    }

    private Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Address.class, id));
    }

}
