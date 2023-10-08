package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.VoteRewardEntity;

/**
 * Interface de service pour la gestion des récompenses de vote de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations spécifiques aux récompenses de vote.
 *
 * @author Brian Van Bellinghen
 */
public interface VoteRewardService extends CrudService<VoteRewardEntity, Long> {
}