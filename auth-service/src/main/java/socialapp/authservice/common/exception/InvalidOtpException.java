package socialapp.authservice.common.exception;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException() {
        super("invalid otp");
    }
}
