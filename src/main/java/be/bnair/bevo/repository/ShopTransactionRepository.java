package be.bnair.bevo.repository;

import be.bnair.bevo.models.entities.ShopTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repository pour la gestion des entités de transactions de boutique (ShopTransactionEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités de transactions de boutique en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface ShopTransactionRepository extends JpaRepository<ShopTransactionEntity, Long> {}