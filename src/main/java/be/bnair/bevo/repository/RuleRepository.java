package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.RuleEntity;

/**
 * Interface de repository pour la gestion des entités de règles (RuleEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités de règles en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
}