package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.repository.ShopTransactionRepository;
import be.bnair.bevo.services.ShopTransactionService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des transactions de la boutique de l'application Bevo.
 * Cette classe implémente l'interface {@link ShopTransactionService} et fournit les opérations de gestion des transactions de la boutique.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class ShopTransactionServiceImpl implements ShopTransactionService {
    private final ShopTransactionRepository repository;

    /**
     * Constructeur de la classe ShopTransactionServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des transactions de la boutique.
     */
    public ShopTransactionServiceImpl(ShopTransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle transaction de la boutique.
     *
     * @param creater L'objet {@link ShopTransactionEntity} à créer.
     * @return La transaction de la boutique créée.
     */
    @Override
    public ShopTransactionEntity create(ShopTransactionEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les transactions de la boutique.
     *
     * @return Une liste d'objets {@link ShopTransactionEntity}.
     */
    @Override
    public List<ShopTransactionEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une transaction de la boutique par son identifiant.
     *
     * @param id L'identifiant de la transaction de la boutique à récupérer.
     * @return La transaction de la boutique correspondante, ou {@link Optional#empty()} si aucune transaction n'est trouvée.
     */
    @Override
    public Optional<ShopTransactionEntity> getOneById(Long id) {
        return repository.findById(id);
    }
}
