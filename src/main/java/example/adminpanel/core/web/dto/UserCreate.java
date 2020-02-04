package example.adminpanel.core.web.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import example.adminpanel.core.dto.UserAddress;

@Valid
public class UserCreate {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Past
    @NotNull
    private LocalDate birthday;
    @Size(min = 4, max = 20)
    @NotNull
    private String login;
    @Size(min = 6, max = 20)
    @NotNull
    private String password;
    @Nullable
    private String aboutUser;
    @NotNull
    @Valid
    private UserAddress address;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Nullable
    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(
            @Nullable
                    String aboutUser
    ) {
        this.aboutUser = aboutUser;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }
}
