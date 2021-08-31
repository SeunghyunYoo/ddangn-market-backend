package com.ddangnmarket.ddangmarkgetbackend.login.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotEmpty
    @Email
    private String mail;
    @NotEmpty
    @Length(min=8, max=20)
    private String password;
}
