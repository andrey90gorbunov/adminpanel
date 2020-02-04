/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.web.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import example.adminpanel.core.dto.Role;
import example.adminpanel.core.dto.UserAddress;

public class UserUpdate {
    private String firstName;
    private String lastName;
    @Past
    private LocalDate birthday;
    @Size(min = 4, max = 20)
    private String password;
    private String aboutUser;
    @Valid
    private UserAddress address;
    private Role role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
