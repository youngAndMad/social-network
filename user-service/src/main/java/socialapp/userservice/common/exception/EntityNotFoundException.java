package socialapp.userservice.common.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass, Long id) {
        super("entity %s with id %d not found".formatted(entityClass.getSimpleName(), id));
    }

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super("entity %s with id %s not found".formatted(entityClass.getSimpleName(), id.toString()));
    }
}
