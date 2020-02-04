package example.adminpanel.core.web.assemblers.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import example.adminpanel.core.dto.User;
import example.adminpanel.core.web.assemblers.AbstractViewAssembler;
import example.adminpanel.core.web.assemblers.UserViewAssembler;
import example.adminpanel.core.web.dto.UserView;

@Component
public class UserViewAssemblerImpl extends AbstractViewAssembler<User, UserView> implements UserViewAssembler {

    @Override
    public UserView toView(User user) {
        if (user == null) {
            return null;
        }
        UserView userView = new UserView();
        userView.setUserId(user.getId());
        userView.setLogin(user.getLogin());
        userView.setFirstName(user.getFirstName());
        userView.setLastName(user.getLastName());
        userView.setBirthday(user.getBirthday());
        userView.setAddress(user.getAddress());
        userView.setAboutUser(user.getAboutUser());
        userView.setRole(user.getRole().name());
        return userView;

    }

    @Override
    public Page<UserView> mapPageView(Page<User> objectEntityPage) {
        return super.mapPageView(objectEntityPage);
    }

    @Override
    public UserView toUserView(User user) {
        return toView(user);
    }

    @Override
    public Page<UserView> toUserViewPage(Page<User> userPage) {
        return mapPageView(userPage);
    }
}
