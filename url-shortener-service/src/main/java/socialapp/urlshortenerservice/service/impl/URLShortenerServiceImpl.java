package socialapp.urlshortenerservice.service.impl;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import socialapp.urlshortenerservice.exception.URLNotFoundException;
import socialapp.urlshortenerservice.model.dto.URLRequest;
import socialapp.urlshortenerservice.model.dto.URLResponse;
import socialapp.urlshortenerservice.service.URLShortenerService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class URLShortenerServiceImpl implements URLShortenerService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public URLResponse createShortURL(URLRequest urlRequest) {
        String URL = urlRequest.URL();
        if (urlIsValid(URL)) {
            String id = generateId(URL);
            redisTemplate.opsForValue().set(id, URL);
            return new URLResponse(id);
        } else {
            throw new RuntimeException("URL invalid " + URL);
        }
    }

    @Override
    public RedirectView redirect(String shortURL) {
        Optional<String> url = Optional.ofNullable(redisTemplate.opsForValue().get(shortURL));
        if (url.isPresent()) {
            return new RedirectView(url.get());
        } else {
            throw new URLNotFoundException(shortURL);
        }
    }

    @Override
    public String generateId(String URL) {
        byte[] hash = Hashing.sha256().hashString(URL, StandardCharsets.UTF_8).asBytes();
        String base64Encoded = Base64.getUrlEncoder().encodeToString(hash);
        int desiredLength = 8;
        return base64Encoded.substring(0, desiredLength);
    }

    @Override
    public Boolean urlIsValid(String URL) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );
        return urlValidator.isValid(URL);
    }
}
