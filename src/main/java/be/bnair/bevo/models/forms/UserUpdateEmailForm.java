package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de mise à jour d'adresse e-mail de l'utilisateur.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class UserUpdateEmailForm {
    /**
     * L'adresse e-mail actuelle de l'utilisateur.
     */
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String email;

    /**
     * La nouvelle adresse e-mail de l'utilisateur.
     */
    @NotEmpty
    @Email(message = "La nouvelle adresse email ne peut pas être vide.")
    private String new_email;
}
