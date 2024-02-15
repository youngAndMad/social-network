package socialapp.ktuserservice.service.impl

import org.springframework.stereotype.Service
import socialapp.ktuserservice.common.mapper.AddressMapper
import socialapp.ktuserservice.model.dto.AddressDto
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.repository.AddressRepository
import socialapp.ktuserservice.service.AddressService

@Service
class AddressServiceImpl(
    private var addressRepository: AddressRepository,
    private var addressMapper: AddressMapper
) : AddressService {

    override fun save(addressDto: AddressDto): Address =
        addressRepository.save(addressMapper.toModel(addressDto));

    override fun update(addressDto: AddressDto,address: Address) {
        addressMapper.update(address, addressDto);
        addressRepository.save(address);
    }

}