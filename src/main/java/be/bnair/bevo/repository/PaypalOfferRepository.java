package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.PaypalOfferEntity;

@Repository
public interface PaypalOfferRepository extends JpaRepository<PaypalOfferEntity, Long> {
}