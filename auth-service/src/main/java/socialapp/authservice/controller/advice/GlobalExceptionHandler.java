package socialapp.authservice.controller.advice;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import socialapp.authservice.common.exception.EmailNotVerifiedException;
import socialapp.authservice.common.exception.InvalidRequestPayloadException;
import socialapp.authservice.common.exception.OtpExpiredException;
import socialapp.authservice.model.dto.ErrorDetails;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Primary
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            EmailNotVerifiedException.class,
            InvalidRequestPayloadException.class,
            OtpExpiredException.class
    })
    ResponseEntity<ErrorDetails> handle(RuntimeException ex, WebRequest request) {
        var errorDetails = buildErrorDetails(ex, HttpStatus.BAD_REQUEST, request);

        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var errorDetails = buildErrorDetails(ex, HttpStatus.BAD_REQUEST, request);
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        log.info("handleExceptionInternal message = {}", ex.getMessage());
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var errorDetails = ErrorDetails
                .builder()
                .path(getPath(request))
                .message(collectBRErrors(ex.getBindingResult()))
                .thrownAt(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    private String getPath(WebRequest request) {
        return request instanceof ServletWebRequest
                ? ((ServletWebRequest) request).getRequest().getRequestURI()
                : request.getContextPath();
    }

    private String collectBRErrors(BindingResult br) {
        return br.getFieldErrors()
                .stream()
                .map(fe -> fe.getField().concat("-")
                        .concat(fe.getDefaultMessage() == null ? "invalid param" : fe.getDefaultMessage())
                )
                .collect(Collectors.joining(","));
    }

    private ErrorDetails buildErrorDetails(Throwable e, HttpStatus status, WebRequest request) {
        return ErrorDetails.builder()
                .status(status)
                .message(e.getMessage())
                .path(getPath(request))
                .thrownAt(LocalDateTime.now())
                .build();
    }
}
