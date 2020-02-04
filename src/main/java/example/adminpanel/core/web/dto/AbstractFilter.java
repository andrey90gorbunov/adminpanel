/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.web.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

abstract class AbstractFilter {
    public final static Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "login"));

    private Pageable pageable = DEFAULT_PAGEABLE;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

}
