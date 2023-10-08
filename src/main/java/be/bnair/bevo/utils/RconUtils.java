package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.ServerEntity;
import net.kronos.rkon.core.Rcon;

/**
 * Utilitaire pour l'envoi de commandes à un serveur Minecraft via RCON (Remote Console).
 * Cette classe permet d'envoyer des commandes à un serveur Minecraft distant en utilisant
 * le protocole RCON.
 *
 * @author Brian Van Bellinghen
 */
public class RconUtils {

    /**
     * Envoie une commande au serveur Minecraft via RCON.
     *
     * @param entity   L'entité du serveur contenant les informations de connexion RCON.
     * @param command  La commande à envoyer au serveur.
     * @throws Exception Une exception en cas d'erreur lors de l'envoi de la commande.
     */
    public static void sendCommandToServer(ServerEntity entity, String command) throws Exception {
        new Rcon(entity.getServerIp(), entity.getRconPort(), entity.getRconPassword().getBytes()).command(command);
    }
}
