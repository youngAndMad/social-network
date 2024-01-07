package socialapp.authservice.service;

import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.model.entity.User;
import socialapp.authservice.security.AppUserDetails;


public interface AuthService {

    User register(RegistrationDto registrationDto);

    void confirmEmail(Integer otp, AppUserDetails userDetails);

}
