package socialapp.ktuserservice.controller.advice

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.*
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import socialapp.ktuserservice.common.exception.EmailRegisteredYetException

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.localizedMessage)

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(problemDetail)
    }

    @ExceptionHandler(EmailRegisteredYetException::class)
    fun handleEmailRegisteredException(e: EmailRegisteredYetException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.localizedMessage)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(problemDetail)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val problemDetail =
            ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, collectBindingErrors(ex.bindingResult))

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(problemDetail)
    }

    private fun collectBindingErrors(bindingResult: BindingResult): String =
        bindingResult.fieldErrors.joinToString(",") { fieldError -> "${fieldError.field} - ${fieldError.defaultMessage}" }

}