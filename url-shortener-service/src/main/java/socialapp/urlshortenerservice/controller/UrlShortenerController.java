package socialapp.urlshortenerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import socialapp.urlshortenerservice.model.dto.URLRequest;
import socialapp.urlshortenerservice.model.dto.URLResponse;
import socialapp.urlshortenerservice.service.impl.URLShortenerServiceImpl;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final URLShortenerServiceImpl service;

    @GetMapping("/{shortURL}")
    public RedirectView redirect(@PathVariable String shortURL) {
        return service.redirect(shortURL);
    }

    @PostMapping
    public ResponseEntity<URLResponse> create(@RequestBody URLRequest urlRequest) {
        return ResponseEntity.ok(service.createShortURL(urlRequest));
    }
}
