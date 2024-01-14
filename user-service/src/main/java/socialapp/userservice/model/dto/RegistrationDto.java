package socialapp.userservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import socialapp.userservice.model.enums.Gender;

public record RegistrationDto(
        @NotNull @Valid
        AddressDto address,
        @NotNull
        @NotBlank
        String firstName,
        @Email
        String email,
        @NotNull
        @NotBlank
        String lastName,
        @NotNull
        Gender gender,
        @NotBlank
        @Min(12) @Max(150)
        Integer age
) {
}
