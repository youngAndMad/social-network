package socialapp.authservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import socialapp.authservice.common.annotation.Password;

public record ResetPasswordRequestDto(
        @NotNull
        @NotBlank
        String token,
        @Password
        String password
) {
}
