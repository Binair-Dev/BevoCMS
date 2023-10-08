package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.repository.RuleRepository;
import be.bnair.bevo.services.RuleService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des règles de l'application Bevo.
 * Cette classe implémente l'interface {@link RuleService} et fournit les opérations de gestion des règles.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class RuleServiceImpl implements RuleService {
    private final RuleRepository repository;

    /**
     * Constructeur de la classe RuleServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des règles.
     */
    public RuleServiceImpl(RuleRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle règle.
     *
     * @param creater L'objet {@link RuleEntity} à créer.
     * @return La règle créée.
     */
    @Override
    public RuleEntity create(RuleEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les règles.
     *
     * @return Une liste d'objets {@link RuleEntity}.
     */
    @Override
    public List<RuleEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une règle par son identifiant.
     *
     * @param id L'identifiant de la règle à récupérer.
     * @return La règle correspondante, ou {@link Optional#empty()} si aucune règle n'est trouvée.
     */
    @Override
    public Optional<RuleEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime une règle par son identifiant.
     *
     * @param id L'identifiant de la règle à supprimer.
     * @throws Exception Si la règle avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour une règle existante.
     *
     * @param id      L'identifiant de la règle à mettre à jour.
     * @param updater L'objet {@link RuleEntity} contenant les données de mise à jour.
     * @return La règle mise à jour.
     * @throws Exception Si la règle avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public RuleEntity update(Long id, RuleEntity updater) throws Exception {
        Optional<RuleEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            RuleEntity ruleEntity = entity.get();
            ruleEntity.setTitle(updater.getTitle());
            ruleEntity.setDescription(updater.getDescription());
            return repository.save(ruleEntity);
        }
        throw new Exception("Impossible de trouver la règle avec l'identifiant : " + id);
    }
}
