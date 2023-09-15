package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateEmailForm {
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String email;
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String new_email;
}
