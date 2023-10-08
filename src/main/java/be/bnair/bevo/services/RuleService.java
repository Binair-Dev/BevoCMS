package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.RuleEntity;

/**
 * Interface de service pour la gestion des entités de type {@link RuleEntity}.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux règles de l'application.
 *
 * @author Brian Van Bellinghen
 */
public interface RuleService extends CrudService<RuleEntity, Long> {
}