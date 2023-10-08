package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Cette classe représente un formulaire d'utilisateur.
 *
 * @author Brian Van Bellinghen
 */
@Data
public class UserForm {
    /**
     * Le nom d'utilisateur.
     */
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le nom d'utilisateur doit contenir entre 3 et 16 caractères.")
    private String username;

    /**
     * L'adresse e-mail de l'utilisateur.
     */
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String email;

    /**
     * Le mot de passe de l'utilisateur.
     */
    @NotEmpty(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, max = 16, message = "Le mot de passe doit contenir entre 8 et 16 caractères.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$", message = "Le mot de passe doit contenir au moins une lettre en majuscule, un chiffre et un caractère spécial")
    private String password;

    /**
     * Le rang de l'utilisateur.
     */
    @Min(value = 0, message = "Le rang ne peut pas être vide")
    private long rank;

    /**
     * Les crédits de l'utilisateur.
     */
    @Min(value = 0, message = "Les crédits ne peuvent pas être vide")
    private long credit;

    /**
     * Indique si l'utilisateur a été confirmé ou non.
     */
    @NotNull(message = "Vous devez spécifier si l'utilisateur a été confirmé ou non")
    private boolean confirmed;

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
        userEntity.setConfirmed(confirmed);
        userEntity.setCredit(credit);
        return userEntity;
    }
}

