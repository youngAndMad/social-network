package socialapp.authservice.service;

import socialapp.authservice.model.dto.EmailVerificationRequestDto;
import socialapp.authservice.model.entity.User;
import socialapp.authservice.model.dto.RegistrationDto;


public interface AuthService {

    User register(RegistrationDto registrationDto);

    void confirmEmail(EmailVerificationRequestDto emailVerificationRequestDto);

}
