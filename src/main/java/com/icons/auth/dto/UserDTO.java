package com.icons.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Email(message = "Username must be an email")
    private String username;

    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
}
