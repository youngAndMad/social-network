package socialapp.urlshortenerservice.exception;

/**
 * Exception thrown when an invalid URL is encountered.
 *
 * This exception is a subclass of RuntimeException and is typically used to indicate that a URL is not valid.
 * It includes the invalid URL as part of the error message.
 */
public class URLNotValidException extends RuntimeException {
    /**
     * Constructs a new URLNotValidException with the specified invalid URL.
     *
     * @param URL The invalid URL that triggered the exception.
     */
    public URLNotValidException(String URL) {
        super("URL invalid " + URL);
    }
}
