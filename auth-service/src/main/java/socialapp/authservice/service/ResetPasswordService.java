package socialapp.authservice.service;

import socialapp.authservice.model.dto.ResetPasswordRequestDto;
import socialapp.authservice.security.AppUserDetails;

public interface ResetPasswordService {

    void resetRequest(AppUserDetails userDetails);

    void resetAttempt(AppUserDetails userDetails, ResetPasswordRequestDto resetPasswordRequestDto);

}
