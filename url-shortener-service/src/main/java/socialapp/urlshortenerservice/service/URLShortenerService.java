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
     URLResponse createShortURL(URLRequest urlRequest);

    /**
     * Redirects to the original long URL based on the provided short URL.
     *
     * @param shortURL The short URL to be redirected.
     * @return RedirectView to the original long URL.
     */
     RedirectView redirect(String shortURL);
}
