package com.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDto {
    @Email
    @NotNull(message = "Email is required")
    @NotBlank (message = "Email is required")
    private String email;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}",message = "Password must contain one uppercase character and one special character")
    private String password;
    @NotNull(message = "First Name is required")
    @NotBlank (message = "First Name is required")
    private String firstName;
    @NotNull(message = "Last Name  is required")
    @NotBlank (message = "Last Name is required")
    private String lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
