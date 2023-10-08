package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.VoteEntity;

/**
 * Interface de service pour la gestion des votes de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations spécifiques aux votes.
 *
 * @author Brian Van Bellinghen
 */
public interface VoteService extends CrudService<VoteEntity, Long> {
    /**
     * Supprime tous les votes associés à un utilisateur spécifique.
     *
     * @param userId L'ID de l'utilisateur dont les votes doivent être supprimés.
     */
    void deleteAllByUserId(Long userId);

    /**
     * Supprime tous les votes.
     */
    void deleteAll();
}