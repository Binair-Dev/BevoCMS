package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.RankEntity;

/**
 * Interface de service pour la gestion des entités de type {@link RankEntity}.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux rangs d'utilisateurs.
 *
 * @author Brian Van Bellinghen
 */
public interface RankService extends CrudService<RankEntity, Long> {
}
