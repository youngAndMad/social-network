package socialapp.urlshortenerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import socialapp.urlshortenerservice.exception.URLNotFoundException;
import socialapp.urlshortenerservice.dto.UrlRequest;
import socialapp.urlshortenerservice.service.URLShortenerService;

import java.util.Optional;

@RestController
@RequestMapping("/r")
public class UrlShortenerController {

    private final StringRedisTemplate redisTemplate;
    private final URLShortenerService service;

    @Autowired
    public UrlShortenerController(StringRedisTemplate redisTemplate, URLShortenerService service) {
        this.redisTemplate = redisTemplate;
        this.service = service;
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl) {
        Optional<String> url = Optional.ofNullable(redisTemplate.opsForValue().get(shortUrl));
        if (url.isPresent()) {
            return new RedirectView(url.get());
        } else {
            throw new URLNotFoundException(shortUrl);
        }
    }

    @PostMapping
    public String create(@RequestBody UrlRequest urlRequest) {
        String url = urlRequest.url();
        if(service.urlIsValid(url)) {
            String id = service.generateId(url);
            redisTemplate.opsForValue().set(id, url);
            return id;
        }
        else {
            throw new RuntimeException("URL invalid " + url);
        }
    }
}
