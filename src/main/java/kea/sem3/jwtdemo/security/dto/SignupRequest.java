package kea.sem3.jwtdemo.security.dto;

import kea.sem3.jwtdemo.security.UserWithPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = UserWithPassword.USER_NAME_MIN_SIZE, max = UserWithPassword.USER_NAME_MAX_SIZE)
    private String username;

    @NotBlank
    @Size(max = UserWithPassword.EMAIL_MAX_SIZE)
    @Email
    private String email;

    @NotBlank
    @Size(min = UserWithPassword.PASSWORD_MIN_SIZE, max = UserWithPassword.PASSWORD_MAX_SIZE)
    private String password;
}
