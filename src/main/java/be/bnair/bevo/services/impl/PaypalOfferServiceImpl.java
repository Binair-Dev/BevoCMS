package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.repository.PaypalOfferRepository;
import be.bnair.bevo.services.PaypalOfferService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des offres Paypal de l'application.
 * Cette classe implémente l'interface {@link PaypalOfferService} et fournit les opérations de gestion des offres Paypal.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class PaypalOfferServiceImpl implements PaypalOfferService {
    private final PaypalOfferRepository repository;

    /**
     * Constructeur de la classe PaypalOfferServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des offres Paypal.
     */
    public PaypalOfferServiceImpl(PaypalOfferRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle offre Paypal.
     *
     * @param creater L'objet {@link PaypalOfferEntity} à créer.
     * @return L'offre Paypal créée.
     */
    @Override
    public PaypalOfferEntity create(PaypalOfferEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les offres Paypal.
     *
     * @return Une liste d'objets {@link PaypalOfferEntity}.
     */
    @Override
    public List<PaypalOfferEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une offre Paypal par son identifiant.
     *
     * @param id L'identifiant de l'offre Paypal à récupérer.
     * @return L'offre Paypal correspondante, ou {@link Optional#empty()} si aucune offre n'est trouvée.
     */
    @Override
    public Optional<PaypalOfferEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime une offre Paypal par son identifiant.
     *
     * @param id L'identifiant de l'offre Paypal à supprimer.
     * @throws Exception Si l'offre Paypal avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour une offre Paypal existante.
     *
     * @param id      L'identifiant de l'offre Paypal à mettre à jour.
     * @param updater L'objet {@link PaypalOfferEntity} contenant les données de mise à jour.
     * @return L'offre Paypal mise à jour.
     * @throws Exception Si l'offre Paypal avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public PaypalOfferEntity update(Long id, PaypalOfferEntity updater) throws Exception {
        Optional<PaypalOfferEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            PaypalOfferEntity paypalOfferEntity = optional.get();
            paypalOfferEntity.setTitle(updater.getTitle());
            paypalOfferEntity.setDescription(updater.getDescription());
            paypalOfferEntity.setPrice(updater.getPrice());
            paypalOfferEntity.setCredit(updater.getCredit());
            return repository.save(paypalOfferEntity);
        }
        throw new Exception("Impossible de trouver l'offre Paypal avec l'identifiant : " + id);
    }
}
