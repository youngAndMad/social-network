package socialapp.ktuserservice.model.dto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddressDto(
    @field:NotNull
    @field:NotBlank
    var country: String,

    @field:NotNull
    @field:NotBlank
    var city: String
)
