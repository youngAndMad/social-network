package socialapp.chatservice.common.annotation;

import jakarta.validation.Constraint;
import socialapp.chatservice.utils.validator.BearerTokenValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BearerTokenValidator.class)
public @interface BearerToken {
}
