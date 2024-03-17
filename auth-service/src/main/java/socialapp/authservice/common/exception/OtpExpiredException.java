package socialapp.authservice.common.exception;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException() {
        super("otp has expired");
    }
}
