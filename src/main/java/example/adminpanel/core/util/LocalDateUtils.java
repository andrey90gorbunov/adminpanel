/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.util;

import java.time.LocalDate;
import java.util.Optional;

import com.google.common.collect.Range;

public class LocalDateUtils {
    public static Range<LocalDate> toRange(Optional<LocalDate> dateBefore, Optional<LocalDate> dateAfter) {
        if (!dateBefore.isPresent() && !dateAfter.isPresent()) {
            return null;
        } else {
            return dateBefore.map(date -> dateAfter.map(localDate -> Range.closed(date, localDate)).orElseGet(() -> Range.greaterThan(date)))
                             .orElseGet(() -> Range.lessThan(dateAfter.get()));
        }
    }
}
