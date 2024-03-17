package socialapp.authservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import socialapp.authservice.common.annotation.Password;

public record RegistrationDto(
        String firstName,
        String lastName,
        @Email
        String email,
//        @Password
        String password,
        String phone,
        @NotBlank
        @NotNull
        String username,
        String tag
) {
}
