package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.ShopCategoryEntity;

/**
 * Interface de service pour la gestion des catégories de la boutique de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux catégories de la boutique.
 *
 * @author Brian Van Bellinghen
 */
public interface ShopCategoryService extends CrudService<ShopCategoryEntity, Long> {
}