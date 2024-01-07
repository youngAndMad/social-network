package socialapp.urlshortenerservice.exception;

/**
 * Exception class representing the scenario where a URL is not found in the application.
 */
public class URLNotFoundException extends RuntimeException{
    /**
     * Constructs a new URLNotFoundException with a specific message indicating the absence of the URL with the given id.
     *
     * @param id The id of the URL that was not found.
     */
    public URLNotFoundException(String id) {
        super("URL not found with id " + id);
    }
}