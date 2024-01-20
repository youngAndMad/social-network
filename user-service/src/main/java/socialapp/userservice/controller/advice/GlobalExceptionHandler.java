package socialapp.userservice.controller.advice;

import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import socialapp.userservice.common.exception.EmailRegisteredYetException;
import socialapp.userservice.common.exception.EntityNotFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler({EmailRegisteredYetException.class})
    ResponseEntity<ProblemDetail> handleEmailRegisteredException(EmailRegisteredYetException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),collectBindingErrors(ex.getBindingResult()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    private String collectBindingErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(","));
    }
}
