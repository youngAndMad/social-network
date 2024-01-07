package socialapp.urlshortenerservice.exception;

public class URLNotFoundException extends RuntimeException{
    public URLNotFoundException(String id) {
        super("URL not found with id " + id);
    }
}