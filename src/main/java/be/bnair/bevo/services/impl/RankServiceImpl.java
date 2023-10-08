package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.repository.RankRepository;
import be.bnair.bevo.services.RankService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des rangs de l'application Bevo.
 * Cette classe implémente l'interface {@link RankService} et fournit les opérations de gestion des rangs.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class RankServiceImpl implements RankService {
    private final RankRepository repository;

    /**
     * Constructeur de la classe RankServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des rangs.
     */
    public RankServiceImpl(RankRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée un nouveau rang.
     *
     * @param creater L'objet {@link RankEntity} à créer.
     * @return Le rang créé.
     */
    @Override
    public RankEntity create(RankEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de tous les rangs.
     *
     * @return Une liste d'objets {@link RankEntity}.
     */
    @Override
    public List<RankEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère un rang par son identifiant.
     *
     * @param id L'identifiant du rang à récupérer.
     * @return Le rang correspondant, ou {@link Optional#empty()} si aucun rang n'est trouvé.
     */
    @Override
    public Optional<RankEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime un rang par son identifiant.
     *
     * @param id L'identifiant du rang à supprimer.
     * @throws Exception Si le rang avec l'identifiant spécifié n'est pas trouvé.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour un rang existant.
     *
     * @param id      L'identifiant du rang à mettre à jour.
     * @param updater L'objet {@link RankEntity} contenant les données de mise à jour.
     * @return Le rang mis à jour.
     * @throws Exception Si le rang avec l'identifiant spécifié n'est pas trouvé.
     */
    @Override
    public RankEntity update(Long id, RankEntity updater) throws Exception {
        Optional<RankEntity> rank = repository.findById(id);
        if (rank.isPresent()) {
            RankEntity rankEntity = rank.get();
            rankEntity.setTitle(updater.getTitle());
            rankEntity.setPower(updater.getPower());
            return repository.save(rankEntity);
        }
        throw new Exception("Impossible de trouver le rang avec l'identifiant : " + id);
    }
}
