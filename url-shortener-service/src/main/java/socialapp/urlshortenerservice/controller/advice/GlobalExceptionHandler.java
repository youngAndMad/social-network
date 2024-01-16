package socialapp.urlshortenerservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import socialapp.urlshortenerservice.exception.URLNotFoundException;
import socialapp.urlshortenerservice.exception.URLNotValidException;

/**
 * Global exception handler for handling uncaught exceptions in the application.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles URLNotFoundException by logging the error and returning a not found response.
     *
     * @param e The URLNotFoundException that occurred.
     * @return ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<String> handleURLNotFoundException(Exception e) {
        log.error("URL not found:", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found." + e.getMessage());
    }

    /**
     * Handles URLNotValidException by logging the error and returning a bad request response.
     *
     * @param e The URLNotValidException that occurred.
     * @return ResponseEntity with a bad request status and an error message.
     */
    @ExceptionHandler(URLNotValidException.class)
    public ResponseEntity<String> handleURLNotValidException(URLNotValidException e) {
        log.error("Invalid URL:", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid URL." + e.getMessage());
    }
}
