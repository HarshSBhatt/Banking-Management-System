package asd.group2.bms.payload;

import javax.validation.constraints.*;

/**
 * @description: Structure of signup request
 */
public class SignUpRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 40)
    private String name;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank(message = "Email is required")
    @Size(max = 40)
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
