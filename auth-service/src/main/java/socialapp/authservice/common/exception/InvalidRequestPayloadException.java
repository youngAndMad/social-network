package socialapp.authservice.common.exception;

public class InvalidRequestPayloadException extends RuntimeException{
    public InvalidRequestPayloadException(String message) {
        super(message);
    }
}
