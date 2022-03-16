package kea.sem3.jwtdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter  @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String street;
    String city;
    String zip;
}
