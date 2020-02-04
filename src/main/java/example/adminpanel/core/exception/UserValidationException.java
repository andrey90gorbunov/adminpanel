package example.adminpanel.core.exception;

import java.util.function.Supplier;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserValidationException extends ValidationException {

    public UserValidationException(String message) {
        super(message);
    }

    public <T> UserValidationException(ConstraintViolation<T> violation) {
        super(getValidationMessage(violation));
    }

    public static Supplier<UserValidationException> loginAlreadyExists(String login) {
        return () -> new UserValidationException(String.format("Login %s already exists", login));
    }

    public static <T> String getValidationMessage(ConstraintViolation<T> violation) {
        String property = violation.getPropertyPath().toString();
        String message = violation.getMessage();
        return String.format("%s %s", property, message);
    }
}
