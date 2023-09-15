package be.bnair.bevo.repository;

import be.bnair.bevo.models.entities.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.ShopTransactionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopTransactionRepository extends JpaRepository<ShopTransactionEntity, Long> {}