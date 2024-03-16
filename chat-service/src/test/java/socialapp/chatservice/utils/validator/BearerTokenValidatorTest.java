package socialapp.chatservice.utils.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class BearerTokenValidatorTest {

    private BearerTokenValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new BearerTokenValidator();
    }

    @Test
    void isValid_withNullString_returnsFalse() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void isValid_withNonBearerToken_returnsFalse() {
        assertFalse(validator.isValid("NotBearerToken", context));
    }

    @Test
    void isValid_withBearerToken_returnsTrue() {
        assertTrue(validator.isValid("Bearer Token", context));
    }
}