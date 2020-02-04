/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.web.assemblers;

import org.springframework.data.domain.Page;

import example.adminpanel.core.dto.User;
import example.adminpanel.core.web.dto.UserView;

public interface UserViewAssembler {

    UserView toUserView(User user);

    Page<UserView> toUserViewPage(Page<User> userPage);

}
