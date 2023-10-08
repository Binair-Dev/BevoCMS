package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.ShopItemEntity;

/**
 * Interface de service pour la gestion des articles de la boutique de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux articles de la boutique.
 *
 * @author Brian Van Bellinghen
 */
public interface ShopItemService extends CrudService<ShopItemEntity, Long> {
}