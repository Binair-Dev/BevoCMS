package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.bnair.bevo.models.entities.NewsEntity;

/**
 * Interface de repository pour la gestion des entités de nouvelles (NewsEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités de nouvelles en utilisant Spring Data JPA.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {}