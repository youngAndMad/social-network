package socialapp.urlshortenerservice.service;

import org.springframework.web.servlet.view.RedirectView;
import socialapp.urlshortenerservice.model.dto.URLRequest;
import socialapp.urlshortenerservice.model.dto.URLResponse;

/**
 * Service interface for URL shortening and redirection operations.
 */
public interface URLShortenerService {

    /**
     * Creates a short URL based on the provided long URL.
     *
     * @param urlRequest The request containing the long URL to be shortened.
     * @return URLResponse with the response containing the short URL.
     */
    public URLResponse createShortURL(URLRequest urlRequest);

    /**
     * Redirects to the original long URL based on the provided short URL.
     *
     * @param shortURL The short URL to be redirected.
     * @return RedirectView to the original long URL.
     */
    public RedirectView redirect(String shortURL);

    /**
     * Generates a unique identifier for a given URL.
     *
     * @param URL The URL for which the identifier is generated.
     * @return The generated unique identifier.
     */
    public String generateId(String URL);

    /**
     * Checks if a given URL is valid.
     *
     * @param URL The URL to be validated.
     * @return true if the URL is valid, false otherwise.
     */
    public Boolean urlIsValid(String URL);
}
