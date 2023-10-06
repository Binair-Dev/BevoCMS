package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire d'inscription utilisateur.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
@PasswordMatchConstraint(message = "Le mot de passe et la confirmation du mot de passe doivent être identiques.")
public class RegisterForm {
    /**
     * Le nom d'utilisateur saisi lors de l'inscription.
     */
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le nom d'utilisateur doit contenir entre 3 et 16 caractères.")
    private String username;

    /**
     * L'adresse e-mail de l'utilisateur lors de l'inscription.
     */
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String email;

    /**
     * Le mot de passe saisi lors de l'inscription.
     */
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Le mot de passe doit contenir au moins une lettre en majuscule, un chiffre et un caractère spécial")
    private String password;

    /**
     * La confirmation du mot de passe saisi lors de l'inscription.
     */
    @NotEmpty(message = "La confirmation du mot de passe ne peut pas être vide.")
    private String confirmPassword;

    /**
     * Convertit le formulaire en une entité UserEntity.
     *
     * @return Une instance de UserEntity basée sur les données du formulaire.
     */
    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname(username);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setEnabled(true);
        return userEntity;
    }
}
