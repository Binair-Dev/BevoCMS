package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ServerEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création de serveur.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
public class ServerForm {
    /**
     * Le titre du serveur.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * L'adresse IP du serveur.
     */
    @NotEmpty(message = "L'IP ne peut pas être vide.")
    @Size(min = 3, message = "L'IP doit contenir au moins 3 caractères")
    private String serverIp;

    /**
     * Le port du serveur.
     */
    @Min(value = 1, message = "Le port ne peut être vide ou négatif.")
    private int serverPort;

    /**
     * Le port RCON du serveur.
     */
    @Min(value = 1, message = "Le port ne peut être vide ou négatif.")
    private int rconPort;

    /**
     * Le mot de passe RCON du serveur.
     */
    @NotEmpty(message = "Le mot de passe RCON ne peut pas être vide.")
    @Size(min = 3, message = "Le mot de passe RCON doit contenir au moins 3 caractères")
    private String rconPassword;

    /**
     * Convertit le formulaire en une entité ServerEntity.
     *
     * @return Une instance de ServerEntity basée sur les données du formulaire.
     */
    public ServerEntity toEntity() {
        ServerEntity entity = new ServerEntity();
        entity.setTitle(title);
        entity.setServerIp(serverIp);
        entity.setServerPort(serverPort);
        entity.setRconPort(rconPort);
        entity.setRconPassword(rconPassword);
        return entity;
    }
}
