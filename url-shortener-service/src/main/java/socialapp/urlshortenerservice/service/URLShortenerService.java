package socialapp.urlshortenerservice.service;

import org.springframework.web.servlet.view.RedirectView;
import socialapp.urlshortenerservice.model.dto.URLRequest;
import socialapp.urlshortenerservice.model.dto.URLResponse;

public interface URLShortenerService {

    public URLResponse createShortURL(URLRequest urlRequest);

    public RedirectView redirect(String shortURL);

    public String generateId(String URL);

    public Boolean urlIsValid(String URL);
}
