package socialapp.authservice.model.dto;

public record EmailVerificationRequestDto(
        String email,
        Integer otp
) {
}
