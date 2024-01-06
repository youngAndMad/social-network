package socialapp.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import socialapp.authservice.model.dto.EmailVerificationRequestDto;
import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.security.AppUserDetails;
import socialapp.authservice.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("registration")
    ResponseEntity<?> register(@RequestBody @Valid RegistrationDto registrationDto) {
        return ResponseEntity.status(201)
                .body(authService.register(registrationDto));
    }

    @PostMapping("confirm-email")
    ResponseEntity<?> confirmEmail(@RequestBody EmailVerificationRequestDto emailVerificationRequestDto) {
        authService.confirmEmail(emailVerificationRequestDto);
        return ResponseEntity.status(200)
                .build();
    }


    @GetMapping("/me")
    ResponseEntity<?> me(UsernamePasswordAuthenticationToken principal) {
        return ResponseEntity.ok(((AppUserDetails) principal.getPrincipal()).getUser());
    }

}
