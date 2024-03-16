package socialapp.chatservice.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import socialapp.chatservice.common.annotation.BearerToken;

/**
 * This class is a validator for Bearer tokens. It implements the ConstraintValidator interface
 * and overrides the isValid method to check if a given string is a valid Bearer token.
 */
public class BearerTokenValidator implements ConstraintValidator<BearerToken,String> {

    /**
     * This method checks if a given string is a valid Bearer token.
     * A valid Bearer token is not null and starts with "Bearer ".
     *
     * @param bearerToken the string to be checked
     * @param ctx the context in which the constraint is evaluated
     * @return true if the string is a valid Bearer token, false otherwise
     */
    @Override
    public boolean isValid(String bearerToken, ConstraintValidatorContext ctx) {
        return bearerToken != null && bearerToken.startsWith("Bearer ");
    }

}