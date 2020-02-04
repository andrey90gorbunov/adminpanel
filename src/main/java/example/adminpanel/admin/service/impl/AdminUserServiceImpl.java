/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.admin.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import example.adminpanel.admin.repository.AdminUserRepository;
import example.adminpanel.admin.repository.UserPredicate;
import example.adminpanel.admin.service.AdminUserService;
import example.adminpanel.core.dto.Role;
import example.adminpanel.core.dto.User;
import example.adminpanel.core.exception.UserNotFoundException;
import example.adminpanel.core.exception.UserUpdatedException;
import example.adminpanel.core.exception.UserValidationException;
import example.adminpanel.core.security.CustomUserDetails;
import example.adminpanel.core.util.UserValidator;
import example.adminpanel.core.web.dto.UserCreate;
import example.adminpanel.core.web.dto.UserFilter;
import example.adminpanel.core.web.dto.UserUpdate;

import static example.adminpanel.core.exception.UserValidationException.loginAlreadyExists;
import static example.adminpanel.core.util.ObjectUtils.setIfNotEmpty;
import static example.adminpanel.core.util.ObjectUtils.setIfNotNull;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserCreate userCreate)
            throws UserValidationException {
        UserValidator.validate(userCreate);
        if (adminUserRepository.existsByLogin(userCreate.getLogin())) {
            throw loginAlreadyExists(userCreate.getLogin()).get();
        }
        User newUser = toUser(userCreate);
        adminUserRepository.save(newUser);
        return newUser;

    }

    @Override
    public User getUserById(String userId)
            throws UserNotFoundException {
        return adminUserRepository.findById(userId).orElseThrow(UserNotFoundException.onId(userId));
    }

    @Override
    public Page<User> getUsers(UserFilter userFilter) {
        UserPredicate userPredicate = new UserPredicate();
        Predicate predicate = userPredicate.filterPredicate(userFilter);
        if (predicate == null) {
            return adminUserRepository.findAll(userFilter.getPageable());
        }
        return adminUserRepository.findAll(userPredicate.filterPredicate(userFilter), userFilter.getPageable());
    }

    @Override
    public void deleteUser(String userId)
            throws UserNotFoundException {
        User user = getUserById(userId);
        if (getCurrentAdminLogin().isPresent()) {
            if (user.getLogin().equals(getCurrentAdminLogin().get())) {
                throw new UserValidationException("Cannot delete current user");
            }
        }
        adminUserRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId, UserUpdate userUpdate)
            throws UserNotFoundException, UserUpdatedException {
        UserValidator.validate(userUpdate);
        User user = getUserById(userId);
        if (userUpdate.getRole() == Role.ROLE_USER && getCurrentAdminLogin().isPresent() && user.getLogin().equals(getCurrentAdminLogin().get())) {
            throw new UserUpdatedException("Cannot change a role to ROLE_USER for current admin account");
        }
        updateUserData(user, userUpdate);
        adminUserRepository.save(user);
        return user;
    }

    private User toUser(UserCreate userCreate) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(userCreate.getFirstName());
        user.setLastName(userCreate.getLastName());
        user.setBirthday(userCreate.getBirthday());
        user.setLogin(userCreate.getLogin());
        user.setPassword(passwordEncoder.encode(userCreate.getPassword()));
        user.setAboutUser(userCreate.getAboutUser());
        user.setAddress(userCreate.getAddress());
        return user;
    }

    private void updateUserData(User user, UserUpdate userUpdate) {
        setIfNotEmpty(user::setFirstName, userUpdate.getFirstName());
        setIfNotEmpty(user::setLastName, userUpdate.getLastName());
        setIfNotNull(user::setBirthday, userUpdate.getBirthday());
        setIfNotNull(user::setAboutUser, user.getAboutUser());
        setIfNotNull(user::setRole, user.getRole());
        if (!isEmpty(userUpdate.getAddress())) {
            user.setAddress(userUpdate.getAddress());
        }
        if (!isEmpty(userUpdate.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }

    }


    private Optional<String> getCurrentAdminLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object myUser = (auth != null) ? auth.getPrincipal() : null;

        if (myUser instanceof CustomUserDetails) {
            return Optional.ofNullable((((CustomUserDetails) myUser).getUser().getLogin()));
        }
        return Optional.empty();

    }
}
