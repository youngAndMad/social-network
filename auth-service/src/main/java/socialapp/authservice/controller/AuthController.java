package socialapp.authservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;
import org.springframework.web.bind.annotation.*;
import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.model.dto.ResetPasswordRequestDto;
import socialapp.authservice.security.AppUserDetails;
import socialapp.authservice.service.AuthService;
import socialapp.authservice.service.ResetPasswordService;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final ResetPasswordService resetPasswordService;

    @PostMapping("registration")
    ResponseEntity<?> register(@RequestBody @Valid RegistrationDto registrationDto) {
        return ResponseEntity.status(CREATED)
                .body(authService.register(registrationDto));
    }

    @GetMapping("reset-password-request")
    ResponseEntity<?> resetPasswordRequest(@AuthenticationPrincipal AppUserDetails userDetails){
        resetPasswordService.resetRequest(userDetails);
        return ResponseEntity.status(OK)
                .build();
    }

    @PostMapping()
    ResponseEntity<?>  resetPasswordAttempt(
            @RequestBody @Valid ResetPasswordRequestDto resetPasswordRequestDto,
            @AuthenticationPrincipal AppUserDetails userDetails
    ){
        resetPasswordService.resetAttempt(userDetails,resetPasswordRequestDto);

        return ResponseEntity.status(OK)
                .build();
    }


    @PostMapping("confirm-email")
    ResponseEntity<?> confirmEmail(
            @RequestParam Integer otp,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        authService.confirmEmail(otp,userDetails);
        return ResponseEntity.status(OK)
                .build();
    }


    @GetMapping("/me")
    ResponseEntity<?> me(HttpServletRequest request) {
        return ResponseEntity.ok(Map.of("response", request.getUserPrincipal()));
    }

}
