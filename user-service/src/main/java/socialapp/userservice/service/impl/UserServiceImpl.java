package socialapp.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialapp.userservice.common.exception.EmailRegisteredYetException;
import socialapp.userservice.common.mapper.UserMapper;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.entity.User;
import socialapp.userservice.repository.UserRepository;
import socialapp.userservice.service.AddressService;
import socialapp.userservice.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AddressService addressService;


    @Override
    public User register(RegistrationDto registrationDto) {
        var existsByEmail = userRepository.existsByEmail(registrationDto.email());

        if (existsByEmail) {
            throw new EmailRegisteredYetException(registrationDto.email());
        }
        var address = addressService.save(registrationDto.address());
        var user = userMapper.toModel(registrationDto, address);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}

