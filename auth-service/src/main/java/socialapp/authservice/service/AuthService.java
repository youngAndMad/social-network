package socialapp.authservice.service;

import socialapp.authservice.model.entity.User;
import socialapp.authservice.model.dto.RegistrationDto;


public interface AuthService {

    User register(RegistrationDto registrationDto);

}
