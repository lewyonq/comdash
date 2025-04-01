package com.avocados.comdash.user.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confirmPassword;
}
