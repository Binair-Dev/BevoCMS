package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.WikiEntity;

/**
 * Interface de service pour la gestion des articles Wiki de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations spécifiques aux articles Wiki.
 *
 * @author Brian Van Bellinghen
 */
public interface WikiService extends CrudService<WikiEntity, Long> {
}