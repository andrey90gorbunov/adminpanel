/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.web.dto;

import static org.springframework.util.StringUtils.isEmpty;

public class AddressFilter {
    private String country;
    private String city;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isFilterEmpty() {
        return isEmpty(country) && isEmpty(city);
    }
}
