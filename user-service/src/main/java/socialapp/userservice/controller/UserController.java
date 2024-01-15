package socialapp.userservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.userservice.model.dto.IsExistsResponse;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.dto.SuggestionResponse;
import socialapp.userservice.model.entity.User;
import socialapp.userservice.service.UserService;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<User> register(
            @RequestBody @Valid RegistrationDto registrationDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(registrationDto));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("suggest")
    ResponseEntity<Set<SuggestionResponse>> fetchSuggestions(
            @RequestParam String query
    ) {
        return ResponseEntity.ok(userService.fetchSuggestions(query));
    }

    @GetMapping("is-exist")
    ResponseEntity<IsExistsResponse> isExist(@RequestParam @Email String email) {
        return ResponseEntity.ok(userService.isExists(email));
    }

    @PatchMapping("{id}/avatar")
    void uploadAvatar(@RequestParam("file") MultipartFile file,
                      @PathVariable Long id
    ) {
        userService.uploadAvatar(file, id);
    }

}
