package socialapp.authservice.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import socialapp.authservice.common.validation.PasswordValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {
    String message() default """
            Too weak password, password should be between 8-30 characters.
            As minimum one digit, one special is required
            """;

    int minLength() default 8;

    int maxLength() default 30;

    boolean requireDigit() default true;

    boolean requireSpecialChar() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
