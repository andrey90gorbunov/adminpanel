/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.admin.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import example.adminpanel.admin.service.AdminUserService;
import example.adminpanel.core.dto.Role;
import example.adminpanel.core.dto.User;
import example.adminpanel.core.exception.UserNotFoundException;
import example.adminpanel.core.exception.UserUpdatedException;
import example.adminpanel.core.util.LocalDateUtils;
import example.adminpanel.core.web.assemblers.UserViewAssembler;
import example.adminpanel.core.web.dto.UserCreate;
import example.adminpanel.core.web.dto.UserFilter;
import example.adminpanel.core.web.dto.UserUpdate;
import example.adminpanel.core.web.dto.UserView;

@RestController
@RequestMapping(value = "/admin/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private UserViewAssembler userViewAssembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView createUser(
            @RequestBody
                    UserCreate userCreate
    ) {
        User user = adminUserService.createUser(userCreate);
        return userViewAssembler.toUserView(user);
    }

    @GetMapping
    public Page<UserView> getUsers(
            @RequestParam(required = false)
                    String login,
            @RequestParam(required = false)
                    String name,
            @RequestParam(required = false)
                    Role role,
            @RequestParam(name = "dateBefore", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateBefore,
            @RequestParam(name = "dateAfter", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateAfter,
            @RequestParam(required = false)
                    String country,
            @RequestParam(required = false)
                    String city,
            @PageableDefault(size = 10, page = 0, sort = {"login"}, direction = Sort.Direction.ASC)
                    Pageable pageable
    ) {
        UserFilter userFilter = new UserFilter();
        userFilter.setLogin(login);
        userFilter.setName(name);
        userFilter.setRole(role);
        userFilter.setPageable(pageable);
        userFilter.setBirthday(LocalDateUtils.toRange(Optional.ofNullable(dateBefore), Optional.ofNullable(dateAfter)));
        userFilter.getAddressFilter().setCountry(country);
        userFilter.getAddressFilter().setCity(city);
        return userViewAssembler.toUserViewPage(adminUserService.getUsers(userFilter));
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public UserView getUserById(
            @PathVariable
                    String userId
    )
            throws UserNotFoundException {
        return userViewAssembler.toUserView(adminUserService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(
            @PathVariable
                    String userId
    )
            throws UserNotFoundException {
        adminUserService.deleteUser(userId);
    }

    @ResponseBody
    @PutMapping("/{userId}")
    public UserView updateUserById(
            @PathVariable
                    String userId,
            @RequestBody
                    UserUpdate userUpdate
    )
            throws UserNotFoundException, UserUpdatedException {
        return userViewAssembler.toUserView(adminUserService.updateUser(userId, userUpdate));
    }
}
