/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.web.dto;


import java.time.LocalDate;

import com.google.common.collect.Range;

import example.adminpanel.core.dto.Role;

public class UserFilter extends AbstractFilter {
    private String login;
    private String name;

    private Role role;
    private Range<LocalDate> birthday;
    private AddressFilter addressFilter = new AddressFilter();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Range<LocalDate> getBirthday() {
        return birthday;
    }

    public void setBirthday(Range<LocalDate> birthday) {
        this.birthday = birthday;
    }

    public AddressFilter getAddressFilter() {
        return addressFilter;
    }

    public void setAddressFilter(AddressFilter addressFilter) {
        this.addressFilter = addressFilter;
    }
}
