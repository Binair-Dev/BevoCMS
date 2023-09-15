package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdatePasswordForm {
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    private String password;
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    private String new_password;
}
