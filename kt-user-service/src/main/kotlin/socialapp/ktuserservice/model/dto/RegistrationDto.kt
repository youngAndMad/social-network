package socialapp.ktuserservice.model.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.model.enums.Gender
import java.time.LocalDate

data class RegistrationDto(
    @field:NotNull
    @field:NotBlank
    val givenName: String,
    @field:NotNull
    @Valid
    val address: Address,
    @field:NotNull
    @field:NotBlank
    val familyName: String,
    @field:NotNull
    @field:NotBlank
    val preferredUsername: String,
    @field:Email
    val email: String,
    @field:NotNull
    val gender: Gender,
    val birthDate: LocalDate
)
