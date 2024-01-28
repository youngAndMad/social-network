package socialapp.ktuserservice.model.dto

import java.time.LocalDate

/*
* public record UserUpdateDto(
        AddressDto address,
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
*/
data class UserUpdateDto(
    val address: AddressDto,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate
)