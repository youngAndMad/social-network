package socialapp.qrservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import socialapp.qrservice.common.exception.QRGenerationException;

/**
 * Global exception handler for handling specific exceptions and returning appropriate responses.
 *
 * @author Daneker.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles the QRGenerationException and returns a ProblemDetail response with status 500.
     *
     * @param e the QRGenerationException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(QRGenerationException.class)
    ProblemDetail handle(QRGenerationException e) {
        return withDetails(e, 500);
    }

    private ProblemDetail withDetails(RuntimeException e, int sc) {
        log.error("created problem details message={} status_code={}", e.getMessage(), sc);
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(sc), e.getMessage());
    }
}
