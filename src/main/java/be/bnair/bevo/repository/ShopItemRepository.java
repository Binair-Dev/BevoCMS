package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.ShopItemEntity;

/**
 * Interface de repository pour la gestion des entités d'articles de boutique (ShopItemEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités d'articles de boutique en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface ShopItemRepository extends JpaRepository<ShopItemEntity, Long> {
}