/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.web.assemblers;

import org.springframework.data.domain.Page;

abstract public class AbstractViewAssembler<F, T> {
    protected abstract T toView(F from);

    protected Page<T> mapPageView(Page<F> objectEntityPage) {
        if (objectEntityPage == null) {
            return Page.empty();
        }
        return objectEntityPage.map(this::toView);
    }
}
