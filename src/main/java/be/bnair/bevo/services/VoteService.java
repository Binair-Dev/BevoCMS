package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.VoteEntity;
import org.springframework.stereotype.Service;

/**
 * Interface de service pour la gestion des votes de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations spécifiques aux votes.
 *
 * @author Brian Van Bellinghen
 */
@Service
public interface VoteService extends CrudService<VoteEntity, Long> {
    /**
     * Supprime tous les votes.
     */
    void deleteAll();
}