package socialapp.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(
        @NotNull
        @NotBlank
        String country,
        @NotNull
        @NotBlank
        String city
) {
}
