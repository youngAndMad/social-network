package socialapp.authservice.common.exception;

public class EmailNotVerifiedException extends RuntimeException {
    public EmailNotVerifiedException(String email) {
        super("email %s not verified".formatted(email));
    }
}
