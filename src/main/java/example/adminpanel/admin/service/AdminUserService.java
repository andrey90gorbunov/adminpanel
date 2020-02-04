package example.adminpanel.admin.service;

import org.springframework.data.domain.Page;

import example.adminpanel.core.dto.User;
import example.adminpanel.core.exception.UserNotFoundException;
import example.adminpanel.core.exception.UserUpdatedException;
import example.adminpanel.core.web.dto.UserCreate;
import example.adminpanel.core.web.dto.UserFilter;
import example.adminpanel.core.web.dto.UserUpdate;

public interface AdminUserService {

    User createUser(UserCreate userCreate);

    User getUserById(String userId)
            throws UserNotFoundException;

    Page<User> getUsers(UserFilter userFilter);

    void deleteUser(String userId)
            throws UserNotFoundException;

    User updateUser(String userId, UserUpdate userUpdate)
            throws UserNotFoundException, UserUpdatedException;
}
