package be.bnair.bevo.models.forms;

import lombok.Data;

@Data
public class RegisterForm {
    private String username;
    private String password;
    private String confirmPassword;
}
