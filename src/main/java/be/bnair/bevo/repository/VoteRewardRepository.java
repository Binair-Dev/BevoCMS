package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.VoteRewardEntity;

/**
 * Interface de repository pour la gestion des entités de récompense de vote (VoteRewardEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités de récompense de vote en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface VoteRewardRepository extends JpaRepository<VoteRewardEntity, Long> {
}