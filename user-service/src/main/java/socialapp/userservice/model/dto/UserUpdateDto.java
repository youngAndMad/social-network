package socialapp.userservice.model.dto;

import java.time.LocalDate;

public record UserUpdateDto(
        AddressDto address,
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
