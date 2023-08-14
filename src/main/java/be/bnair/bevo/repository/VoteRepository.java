package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.VoteEntity;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
}