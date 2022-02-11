package kea.sem3.jwtdemo.entity;

import kea.sem3.jwtdemo.security.UserWithPassword;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

/**********************************************************************************/
/********** IMPORTANT! DON'T CHANGE ANYTHING IN THIS FILE *************************/
/***** EXTEND IT TO CREATE SPECIALIZED USERS (LIKE Members) ***********************/
/********************************************************************************/

@Entity
@Getter
@Setter
@DiscriminatorValue("USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user")
public class BaseUser implements UserWithPassword {

   @Id
   private String username;

   @Email
   @Column(nullable = false, unique = true,length = 50)
   private String email;

   // 72 == Max length of a bcrypt encoded password
   // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
   @Column(nullable = false, length = 72)
   private String password;

   boolean enabled;

   @Enumerated(EnumType.STRING)
   @Column(columnDefinition = "ENUM('USER','ADMIN')")
   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name="user_role")
   List<Role> roles = new ArrayList<>();

   public BaseUser() {
   }

   public BaseUser(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = pwEncoder.encode(password);
      this.enabled = true;
   }

   @Override
   public void setPassword(String password) {
      this.password = pwEncoder.encode(password);
   }

   @Override
   public List<Role> getRoles() {
      return roles;
   }
   @Override
   public void addRole(Role role){
      roles.add(role);
   }

}
