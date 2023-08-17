package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ServerEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServerForm {
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;
    @NotEmpty(message = "L'IP ne peut pas être vide.")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "Le format de l'IP n'est pas valide.")
    private String serverIp;
    @Min(value = 1, message = "Le port ne peut être vide ou négatif.")
    private int serverPort;
    @Min(value = 1, message = "Le port ne peut être vide ou négatif.")
    private int rconPort;
    @NotEmpty(message = "Le mot de passe RCON ne peut pas être vide.")
    @Size(min = 3, message = "Le mot de passe RCON doit contenir au moins 3 caractères")
    private String rconPassword;

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
