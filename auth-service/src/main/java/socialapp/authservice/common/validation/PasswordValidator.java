package socialapp.authservice.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import socialapp.authservice.common.annotation.Password;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength;
    private int maxLength;
    private boolean requireDigit;
    private boolean requireSpecialChar;

    @Override
    public void initialize(Password constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
        this.requireDigit = constraintAnnotation.requireDigit();
        this.requireSpecialChar = constraintAnnotation.requireSpecialChar();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext ctx) {
        if (password == null) {
            return false;
        }

        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        if (requireDigit && !containsDigit(password)) {
            return false;
        }

        return !requireSpecialChar || containsSpecialChar(password);
    }

    private boolean containsDigit(String s) {
        return s.matches(".*\\d.*");
    }

    private boolean containsSpecialChar(String s) {
        return Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(s).find();
    }

}
