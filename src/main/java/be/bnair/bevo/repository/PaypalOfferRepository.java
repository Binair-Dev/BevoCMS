package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.PaypalOfferEntity;

/**
 * Interface de repository pour la gestion des entités d'offres Paypal (PaypalOfferEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités d'offres Paypal en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface PaypalOfferRepository extends JpaRepository<PaypalOfferEntity, Long> {
}