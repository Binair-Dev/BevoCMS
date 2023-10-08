package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.repository.TransactionRepository;
import be.bnair.bevo.services.TransactionService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des transactions de l'application Bevo.
 * Cette classe implémente l'interface {@link TransactionService} et fournit les opérations de gestion des transactions.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    /**
     * Constructeur de la classe TransactionServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des transactions.
     */
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle transaction.
     *
     * @param creater L'objet {@link TransactionEntity} à créer.
     * @return La transaction créée.
     */
    @Override
    public TransactionEntity create(TransactionEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les transactions.
     *
     * @return Une liste d'objets {@link TransactionEntity}.
     */
    @Override
    public List<TransactionEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une transaction par son identifiant.
     *
     * @param id L'identifiant de la transaction à récupérer.
     * @return La transaction correspondante, ou {@link Optional#empty()} si aucune transaction n'est trouvée.
     */
    @Override
    public Optional<TransactionEntity> getOneById(Long id) {
        return repository.findById(id);
    }
}
