package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.repository.VoteRepository;
import be.bnair.bevo.services.VoteService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des votes de l'application Bevo.
 * Cette classe implémente l'interface {@link VoteService} et fournit les opérations de gestion des votes.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository repository;

    /**
     * Constructeur de la classe VoteServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des votes.
     */
    public VoteServiceImpl(VoteRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée un nouveau vote.
     *
     * @param creater L'objet {@link VoteEntity} à créer.
     * @return Le vote créé.
     */
    @Override
    public VoteEntity create(VoteEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de tous les votes.
     *
     * @return Une liste d'objets {@link VoteEntity}.
     */
    @Override
    public List<VoteEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère un vote par son identifiant.
     *
     * @param id L'identifiant du vote à récupérer.
     * @return Le vote correspondant, ou {@link Optional#empty()} si aucun vote n'est trouvé.
     */
    @Override
    public Optional<VoteEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime un vote par son identifiant.
     *
     * @param id L'identifiant du vote à supprimer.
     * @throws Exception Si le vote n'est pas trouvé.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Supprime tous les votes associés à un utilisateur par son identifiant d'utilisateur.
     *
     * @param userId L'identifiant de l'utilisateur dont les votes doivent être supprimés.
     */
    @Override
    public void deleteAllByUserId(Long userId) {
        repository.deleteAllByUserId(userId);
    }

    /**
     * Supprime tous les votes.
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
