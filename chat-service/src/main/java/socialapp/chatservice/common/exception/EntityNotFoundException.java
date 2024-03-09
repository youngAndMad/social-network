package socialapp.chatservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when an entity is not found.
 * It extends the RuntimeException class, meaning it's an unchecked exception.
 * The class is annotated with @ResponseStatus(HttpStatus.NOT_FOUND),
 * which means that whenever this exception is thrown,
 * the server will respond with a 404 Not Found status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    /**
     * @deprecated
     * This constructor is deprecated and should not be used.
     * Instead, use the constructor that takes a Class and an Object as parameters.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the Throwable.getMessage() method.
     */
    @Deprecated
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityNotFoundException with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to Throwable.initCause(java.lang.Throwable).
     *
     * @param entity the class of the entity that was not found.
     * @param id     the id of the entity that was not found.
     */
    public EntityNotFoundException(Class<?> entity, Object id) {
        super(entity.getSimpleName() + " with id " + id + " not found");
    }

}