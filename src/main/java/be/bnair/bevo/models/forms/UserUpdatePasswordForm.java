package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de mise à jour du mot de passe de l'utilisateur.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class UserUpdatePasswordForm {
    /**
     * Le mot de passe actuel de l'utilisateur.
     */
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Le mot de passe doit contenir au moins une lettre en majuscule, un chiffre et un caractère spécial")
    private String password;

    /**
     * Le nouveau mot de passe de l'utilisateur.
     */
    @NotEmpty(message = "Le nouveau mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le nouveau mot de passe doit contenir entre 8 et 16 caractères.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Le nouveau mot de passe doit contenir au moins une lettre en majuscule, un chiffre et un caractère spécial")
    private String new_password;
}
