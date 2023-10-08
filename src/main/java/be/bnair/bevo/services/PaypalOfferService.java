package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.PaypalOfferEntity;

/**
 * Interface de service pour la gestion des entités de type {@link PaypalOfferEntity}.
 * Cette interface étend {@link CrudService} pour fournir des opérations CRUD spécifiques aux offres Paypal.
 *
 * @author Brian Van Bellinghen
 */
public interface PaypalOfferService extends CrudService<PaypalOfferEntity, Long> {
}
