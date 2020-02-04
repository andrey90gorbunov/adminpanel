/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.util;

import java.util.function.Consumer;

import static org.springframework.util.StringUtils.isEmpty;

public class ObjectUtils {
    public static <T> void setIfNotEmpty(final Consumer<T> setter, final T value) {
        if (!isEmpty(value)) {
            setter.accept(value);
        }
    }

    public static <T> void setIfNotNull(final Consumer<T> setter, final T value) {
        if (!isEmpty(value)) {
            setter.accept(value);
        }
    }
}
