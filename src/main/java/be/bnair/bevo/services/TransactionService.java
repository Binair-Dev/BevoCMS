package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.TransactionEntity;

/**
 * Interface de service pour la gestion des transactions de l'application.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux transactions.
 *
 * @author Brian Van Bellinghen
 */
public interface TransactionService extends CrudService<TransactionEntity, Long> {
}