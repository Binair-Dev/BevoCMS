package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.RankEntity;

/**
 * Interface de repository pour la gestion des entités de rangs (RankEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités de rangs en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface RankRepository extends JpaRepository<RankEntity, Long> {
}