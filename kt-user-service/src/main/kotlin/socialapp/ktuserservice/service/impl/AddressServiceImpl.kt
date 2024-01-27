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
) : AddressService {

    override fun save(addressDto: AddressDto): Address = addressRepository.save(AddressMapper.INSTANCE.toModel(addressDto));

    override fun update(addressDto: AddressDto, id: Long) {
        val address = findById(id);

        AddressMapper.INSTANCE.update(address, addressDto);

        addressRepository.save(address);
    }

    private fun findById(id: Long): Address {
        return addressRepository.findByID(id)
    }
}