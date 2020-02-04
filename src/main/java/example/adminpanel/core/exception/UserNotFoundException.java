/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.exception;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }


    public static Supplier<UserNotFoundException> onId(String userId) {
        return () -> new UserNotFoundException(String.format("User not found by id %s ", userId));
    }


}
