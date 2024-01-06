package socialapp.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import socialapp.authservice.repository.UserRepository;
import socialapp.authservice.model.entity.User;
import socialapp.authservice.security.AppUserDetails;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("registration")
    ResponseEntity<?> register() {
        var user = new User();
        user.setEmail("kkraken2005@gmail.com");
        user.setFirstName("d");
        user.setLastName("d");
        user.setTag("d");
        user.setPhone("d");
        user.setEmailVerified(false);
        user.setUsername("d");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
        return ResponseEntity.status(201)
                .build();
    }

    @GetMapping("/me")
    ResponseEntity<?> me(UsernamePasswordAuthenticationToken principal){
        return ResponseEntity.ok(((AppUserDetails)principal.getPrincipal()).getUser());
    }

}
