package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.repository.VoteRewardRepository;
import be.bnair.bevo.services.VoteRewardService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des récompenses de vote de l'application Bevo.
 * Cette classe implémente l'interface {@link VoteRewardService} et fournit les opérations de gestion des récompenses de vote.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class VoteRewardServiceImpl implements VoteRewardService {
    private final VoteRewardRepository repository;

    /**
     * Constructeur de la classe VoteRewardServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des récompenses de vote.
     */
    public VoteRewardServiceImpl(VoteRewardRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle récompense de vote.
     *
     * @param creater L'objet {@link VoteRewardEntity} à créer.
     * @return La récompense de vote créée.
     */
    @Override
    public VoteRewardEntity create(VoteRewardEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les récompenses de vote.
     *
     * @return Une liste d'objets {@link VoteRewardEntity}.
     */
    @Override
    public List<VoteRewardEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une récompense de vote par son identifiant.
     *
     * @param id L'identifiant de la récompense de vote à récupérer.
     * @return La récompense de vote correspondante, ou {@link Optional#empty()} si aucune récompense de vote n'est trouvée.
     */
    @Override
    public Optional<VoteRewardEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime une récompense de vote par son identifiant.
     *
     * @param id L'identifiant de la récompense de vote à supprimer.
     * @throws Exception Si la récompense de vote n'est pas trouvée.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour une récompense de vote par son identifiant.
     *
     * @param id      L'identifiant de la récompense de vote à mettre à jour.
     * @param updater L'objet {@link VoteRewardEntity} contenant les données de mise à jour.
     * @return La récompense de vote mise à jour.
     * @throws Exception Si la récompense de vote n'est pas trouvée.
     */
    @Override
    public VoteRewardEntity update(Long id, VoteRewardEntity updater) throws Exception {
        Optional<VoteRewardEntity> optionalVote = repository.findById(id);
        if (optionalVote.isPresent()) {
            VoteRewardEntity voteEntity = optionalVote.get();
            voteEntity.setTitle(updater.getTitle());
            voteEntity.setPercent(updater.getPercent());
            voteEntity.setRewardType(updater.getRewardType());
            voteEntity.setCommand(updater.getCommand());
            voteEntity.setCredit(updater.getCredit());
            voteEntity.setServer(updater.getServer());
            return repository.save(voteEntity);
        }
        throw new Exception("Could not find the vote reward with id: " + id);
    }
}
