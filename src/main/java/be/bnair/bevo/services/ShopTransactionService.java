package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.ShopTransactionEntity;

/**
 * Interface de service pour la gestion des transactions de la boutique de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux transactions de la boutique.
 *
 * @author Brian Van Bellinghen
 */
public interface ShopTransactionService extends CrudService<ShopTransactionEntity, Long> {}