package socialapp.urlshortenerservice.service;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class URLShortenerService {

    public String generateId(String url) {
        byte[] hash = Hashing.sha256().hashString(url, StandardCharsets.UTF_8).asBytes();
        String base64Encoded = Base64.getUrlEncoder().encodeToString(hash);
        int desiredLength = 8;
        return base64Encoded.substring(0, desiredLength);
    }

    public Boolean urlIsValid(String url) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );
        return urlValidator.isValid(url);
    }
}
