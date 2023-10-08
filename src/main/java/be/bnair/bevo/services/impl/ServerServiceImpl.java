package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.repository.ServerRepository;
import be.bnair.bevo.services.ServerService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des serveurs de l'application Bevo.
 * Cette classe implémente l'interface {@link ServerService} et fournit les opérations de gestion des serveurs.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository repository;

    /**
     * Constructeur de la classe ServerServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des serveurs.
     */
    public ServerServiceImpl(ServerRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée un nouveau serveur.
     *
     * @param creater L'objet {@link ServerEntity} à créer.
     * @return Le serveur créé.
     */
    @Override
    public ServerEntity create(ServerEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de tous les serveurs.
     *
     * @return Une liste d'objets {@link ServerEntity}.
     */
    @Override
    public List<ServerEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère un serveur par son identifiant.
     *
     * @param id L'identifiant du serveur à récupérer.
     * @return Le serveur correspondant, ou {@link Optional#empty()} si aucun serveur n'est trouvé.
     */
    @Override
    public Optional<ServerEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime un serveur par son identifiant.
     *
     * @param id L'identifiant du serveur à supprimer.
     * @throws Exception Si le serveur avec l'identifiant spécifié n'est pas trouvé.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour un serveur existant.
     *
     * @param id      L'identifiant du serveur à mettre à jour.
     * @param updater L'objet {@link ServerEntity} contenant les données de mise à jour.
     * @return Le serveur mis à jour.
     * @throws Exception Si le serveur avec l'identifiant spécifié n'est pas trouvé.
     */
    @Override
    public ServerEntity update(Long id, ServerEntity updater) throws Exception {
        Optional<ServerEntity> server = repository.findById(id);
        if (server.isPresent()) {
            ServerEntity serverEntity = server.get();
            serverEntity.setTitle(updater.getTitle());
            serverEntity.setServerIp(updater.getServerIp());
            serverEntity.setServerPort(updater.getServerPort());
            serverEntity.setRconPort(updater.getRconPort());
            serverEntity.setRconPassword(updater.getRconPassword());
            return repository.save(serverEntity);
        }
        throw new Exception("Impossible de trouver le serveur avec l'identifiant : " + id);
    }
}
