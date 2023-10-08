package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.NewsEntity;

/**
 * Interface de service pour la gestion des entités de type {@link NewsEntity}.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux actualités.
 *
 * @author Brian Van Bellinghen
 */
public interface NewsService extends CrudService<NewsEntity, Long> {
}
