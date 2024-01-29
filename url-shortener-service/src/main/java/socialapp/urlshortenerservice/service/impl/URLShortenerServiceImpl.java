package socialapp.urlshortenerservice.service.impl;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import socialapp.loggingstarter.annotations.LoggableInfo;
import socialapp.loggingstarter.annotations.LoggableTime;
import socialapp.urlshortenerservice.exception.URLNotFoundException;
import socialapp.urlshortenerservice.exception.URLNotValidException;
import socialapp.urlshortenerservice.model.dto.URLRequest;
import socialapp.urlshortenerservice.model.dto.URLResponse;
import socialapp.urlshortenerservice.service.URLShortenerService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@LoggableTime
@LoggableInfo
public class URLShortenerServiceImpl implements URLShortenerService {

    private final StringRedisTemplate redisTemplate;
    private static final int ACTIVATION_TIME_IN_MINUTES = 10;

    @Override
    public URLResponse createShortURL(URLRequest urlRequest) {
        String URL = urlRequest.url();
        if (urlIsValid(URL)) {
            String id = generateId(URL);
            redisTemplate.opsForValue().set(id, URL);
            redisTemplate.expire(id, ACTIVATION_TIME_IN_MINUTES, TimeUnit.MINUTES);
            return new URLResponse(id);
        } else {
            throw new URLNotValidException(URL);
        }
    }

    @Override
    public RedirectView redirect(String shortURL) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(shortURL))
                .map(RedirectView::new)
                .orElseThrow(() -> new URLNotFoundException(shortURL));
    }

    /**
     * Generates a unique identifier for a given URL.
     *
     * @param URL The URL for which the identifier is generated.
     * @return The generated unique identifier.
     */
    private String generateId(String URL) {
        byte[] hash = Hashing.sha256().hashString(URL, StandardCharsets.UTF_8).asBytes();
        String base64Encoded = Base64.getUrlEncoder().encodeToString(hash);
        int desiredLength = 8;
        return base64Encoded.substring(0, desiredLength);
    }

    /**
     * Checks if a given URL is valid.
     *
     * @param URL The URL to be validated.
     * @return true if the URL is valid, false otherwise.
     */
    private Boolean urlIsValid(String URL) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );
        return urlValidator.isValid(URL);
    }
}
