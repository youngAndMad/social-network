package socialapp.newsservice.payload.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import socialapp.newsservice.payload.exception.EntityNotFoundException;
import socialapp.newsservice.payload.exception.StorageRequestException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleTaskNotFoundException(EntityNotFoundException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(StorageRequestException.class)
    ProblemDetail handleTaskNotFoundException(StorageRequestException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
