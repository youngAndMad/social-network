package socialapp.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.model.dto.ResetPasswordRequestDto;
import socialapp.authservice.model.entity.User;
import socialapp.authservice.security.AppUserDetails;
import socialapp.authservice.service.AuthService;
import socialapp.authservice.service.ResetPasswordService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final ResetPasswordService resetPasswordService;

    @PostMapping("register")
    @ResponseStatus(CREATED)
    User register(@RequestBody @Validated RegistrationDto registrationDto) {
        return authService.register(registrationDto);
    }

    @GetMapping("reset-password-request")
    void resetPasswordRequest(@AuthenticationPrincipal AppUserDetails userDetails) {
        resetPasswordService.resetRequest(userDetails);
    }

    @PostMapping("reset-password/attempt")
    void resetPasswordAttempt(
            @RequestBody @Validated ResetPasswordRequestDto resetPasswordRequestDto,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        resetPasswordService.resetAttempt(userDetails, resetPasswordRequestDto);
    }


    @PostMapping("confirm-email")
    void confirmEmail(
            @RequestParam Integer otp,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        authService.confirmEmail(otp, userDetails);
    }

    @GetMapping("/me")
    AppUserDetails me(@AuthenticationPrincipal AppUserDetails userDetails) {
        return userDetails;
    }

}
