package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.WikiEntity;

/**
 * Interface de repository pour la gestion des entités Wiki (WikiEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités Wiki en utilisant Spring Data JPA.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Repository
public interface WikiRepository extends JpaRepository<WikiEntity, Long> {
}