package socialapp.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<?> register(
            @RequestBody @Valid RegistrationDto registrationDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(registrationDto));
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("suggest")
    ResponseEntity<?> fetchSuggestions(
            @RequestParam String query
    ){
        return ResponseEntity.ok(userService.fetchSuggestions(query));
    }
}
