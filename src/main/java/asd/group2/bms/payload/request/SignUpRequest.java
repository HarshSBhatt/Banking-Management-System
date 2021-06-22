package asd.group2.bms.payload.request;

import asd.group2.bms.model.account.AccountType;
import asd.group2.bms.model.user.RoleType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * @description: Structure of signup request
 */
public class SignUpRequest {
    @NotBlank(message = "First Name is required")
    @Size(min = 3, max = 40)
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 3, max = 40)
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank(message = "Email is required")
    @Size(max = 40)
    @Email(message = "Please enter a valid email")
    private String email;

    @NotNull(message = "Birthdate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15)
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 200)
    private String address;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private RoleType role;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}