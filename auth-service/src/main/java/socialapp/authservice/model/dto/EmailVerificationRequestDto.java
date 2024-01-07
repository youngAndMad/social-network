package socialapp.authservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EmailVerificationRequestDto(
        @Email
        String email,
        @NotNull
        @Min(100_000)
        @Max(999_999)
        Integer otp
) {
}
