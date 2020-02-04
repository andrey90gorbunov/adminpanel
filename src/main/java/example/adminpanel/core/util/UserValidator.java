package example.adminpanel.core.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.util.CollectionUtils;

import example.adminpanel.core.exception.UserValidationException;


public class UserValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> errors = validator.validate(t);
        if (!CollectionUtils.isEmpty(errors)) {
            throw new UserValidationException(CollectionUtils.firstElement(errors));

        }
    }
}
