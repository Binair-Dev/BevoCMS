package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserForm {
    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le nom d'utilisateur doit contenir entre 3 et 16 caractères.")
    private String username;
    @NotEmpty
    @Email(message = "L'adresse email ne peut pas être vide.")
    private String email;
    private String password;
    @Min(value = 0, message = "Le rank donnés ne peut pas être vide")
    private long rank;
    @Min(value = 0, message = "Les crédits donnés ne peut pas être vide")
    private long credit;
    @NotNull(message = "Vous devez spécifier si l'utilisateur a été confirmé ou non")
    private boolean confirmed;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname(username);
        userEntity.setEmail(email);
        if(password.length() > 0)
            userEntity.setPassword(password);
        userEntity.setConfirmed(confirmed);
        userEntity.setCredit(credit);
        return userEntity;
    }
}