package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.ServerEntity;

/**
 * Interface de service pour la gestion des entités de type {@link ServerEntity}.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux serveurs de l'application.
 *
 * @author Brian Van Bellinghen
 */
public interface ServerService extends CrudService<ServerEntity, Long> {
}