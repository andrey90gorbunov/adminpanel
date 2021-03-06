/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserUpdatedException extends Exception {
    public UserUpdatedException(String message) {
        super(message);
    }

}
