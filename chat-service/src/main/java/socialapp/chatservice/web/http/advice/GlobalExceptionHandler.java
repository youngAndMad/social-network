package socialapp.chatservice.web.http.advice;

import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import socialapp.chatservice.common.exception.CreatePrivateChatMemberNotExistException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CreatePrivateChatMemberNotExistException.class})
    ProblemDetail handleCreatePrivateChatMemberNotExistException(
            CreatePrivateChatMemberNotExistException e
    ) {
        return createProblemDetails(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createProblemDetails(HttpStatus.BAD_REQUEST,
                        collectBindingResultErrors(ex.getBindingResult()))
                );
    }

    @Override
    protected ProblemDetail createProblemDetail(Exception ex, HttpStatusCode status, String defaultDetail, String detailMessageCode, Object[] detailMessageArguments, WebRequest request) {
        return super.createProblemDetail(ex, status, defaultDetail, detailMessageCode, detailMessageArguments, request);
    }

    private String collectBindingResultErrors(BindingResult br) {
        return br.getFieldErrors()
                .stream()
                .map(fieldError -> "value %s not acceptable for %s reason: %s".formatted(
                        fieldError.getRejectedValue(),
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.joining(System.lineSeparator(), "[", "]"));
    }

    private static ProblemDetail createProblemDetails(HttpStatus status, String message) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(status.value()),
                message
        );
    }
}
