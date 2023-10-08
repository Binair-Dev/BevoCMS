package be.bnair.bevo.controllers;

import be.bnair.bevo.models.Information;
import be.bnair.bevo.models.dto.UserDTO;
import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.services.ServerService;
import be.bnair.bevo.utils.BevoUtils;
import be.bnair.bevo.utils.MCUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bnair.bevo.services.UserService;

import java.util.List;

/**
 * Contrôleur pour la gestion des informations générales.
 * Ce contrôleur permet de récupérer des informations générales telles que les statistiques des utilisateurs et l'état du serveur.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(value = "/infos")
public class InformationController {
    private final UserService userService;
    private final ServerService serverService;

    /**
     * Constructeur du contrôleur InformationController.
     *
     * @param userService   Service utilisateur pour la gestion des utilisateurs.
     * @param serverService Service de serveur pour la gestion des serveurs.
     */
    public InformationController(UserService userService, ServerService serverService) {
        this.userService = userService;
        this.serverService = serverService;
    }

    /**
     * Endpoint pour récupérer des informations générales, y compris les statistiques des utilisateurs et l'état du serveur.
     *
     * @return Une réponse contenant les informations générales.
     */
    @GetMapping(path = {"/stats"})
    public ResponseEntity<Information> getInformations() {
        long staff = this.userService.getAll().stream()
                .filter(user -> !user.getRank().getTitle().equals("Membre"))
                .map(u -> UserDTO.toDTO(u)).count();
        BevoUtils.view_count++;
        String serverStatus = "";
        List<ServerEntity> servers = this.serverService.getAll();
        if(servers.size() > 0) {
            ServerEntity server = servers.get(0);
            if(MCUtils.isOnline(server.getServerIp(), server.getServerPort()))
                serverStatus = MCUtils.getConnected(server.getServerIp(), server.getServerPort()) + " connectés";
            else serverStatus = "Le serveur est actuellement hors-ligne";
        }
        return ResponseEntity.ok(new Information(this.userService.getAll().stream().count(), BevoUtils.view_count, serverStatus, (int) staff));
    }
}