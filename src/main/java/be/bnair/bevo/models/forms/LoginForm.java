package be.bnair.bevo.models.forms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de connexion utilisateur.
 *
 * @author Brian Van Bellinghen
 */
@Data
public class LoginForm {
    /**
     * Le nom d'utilisateur saisi dans le formulaire.
     */
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le nom d'utilisateur doit contenir entre 3 et 16 caractères.")
    public String username;

    /**
     * Le mot de passe saisi dans le formulaire.
     */
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Le mot de passe doit contenir au moins une lettre en majuscule, un chiffre et un caractère spécial")
    public String password;
}
