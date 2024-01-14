package socialapp.userservice.service;

import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.entity.User;

public interface UserService {

    User register(RegistrationDto registrationDto);

    void delete(Long id);

}
