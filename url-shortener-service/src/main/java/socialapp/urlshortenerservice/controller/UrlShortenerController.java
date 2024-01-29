package socialapp.urlshortenerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import socialapp.urlshortenerservice.model.dto.URLRequest;
import socialapp.urlshortenerservice.model.dto.URLResponse;
import socialapp.urlshortenerservice.service.URLShortenerService;


/**
 * Controller class for handling URL shortening and redirection operations.
 */
@RestController
@Tag(name = "URL Shortener REST API", description = "Endpoints for URL shortening and redirection operations.")
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlShortenerController {

    /**
     * The service responsible for URL shortening and redirection logic.
     */
    private final URLShortenerService service;

    /**
     * Handles GET requests for redirection based on the provided short URL.
     *
     * @param shortURL The short URL to be redirected.
     * @return RedirectView to the original long URL.
     */
    @GetMapping("/{shortURL}")
    @Operation(summary = "Redirect to the original long URL based on the provided short URL")
    public RedirectView redirect(@PathVariable String shortURL) {
        return service.redirect(shortURL);
    }

    /**
     * Handles POST requests to create a short URL based on the provided long URL.
     *
     * @param urlRequest The request containing the long URL to be shortened.
     * @return ResponseEntity with the response containing the short URL.
     */
    @PostMapping
    @Operation(summary = "Create a short URL based on the provided long URL")
    public ResponseEntity<URLResponse> create(@RequestBody URLRequest urlRequest) {
        return ResponseEntity.ok(service.createShortURL(urlRequest));
    }
}
