package be.bnair.bevo.models.forms;

import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatchConstraint(message = "Le mot de passe et la confirmation du mot de passe doivent être identiques.")
public class RegisterForm {
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le nom d'utilisateur doit contenir entre 3 et 16 caractères.")
    private String username;
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String email;
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    private String password;
    @NotEmpty(message = "La confirmation du mot de passe ne peut pas être vide.")
    private String confirmPassword;
}
