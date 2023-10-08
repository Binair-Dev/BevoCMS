package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.WikiEntity;
import be.bnair.bevo.repository.WikiRepository;
import be.bnair.bevo.services.WikiService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des pages Wiki de l'application Bevo.
 * Cette classe implémente l'interface {@link WikiService} et fournit les opérations de gestion des pages Wiki.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class WikiServiceImpl implements WikiService {
    private final WikiRepository repository;

    /**
     * Constructeur de la classe WikiServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des pages Wiki.
     */
    public WikiServiceImpl(WikiRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle page Wiki.
     *
     * @param creater L'objet {@link WikiEntity} à créer.
     * @return La page Wiki créée.
     */
    @Override
    public WikiEntity create(WikiEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les pages Wiki.
     *
     * @return Une liste d'objets {@link WikiEntity}.
     */
    @Override
    public List<WikiEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une page Wiki par son identifiant.
     *
     * @param id L'identifiant de la page Wiki à récupérer.
     * @return La page Wiki correspondante, ou {@link Optional#empty()} si aucune page Wiki n'est trouvée.
     */
    @Override
    public Optional<WikiEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime une page Wiki par son identifiant.
     *
     * @param id L'identifiant de la page Wiki à supprimer.
     * @throws Exception Si la page Wiki n'est pas trouvée.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour une page Wiki par son identifiant.
     *
     * @param id      L'identifiant de la page Wiki à mettre à jour.
     * @param updater L'objet {@link WikiEntity} contenant les données de mise à jour.
     * @return La page Wiki mise à jour.
     * @throws Exception Si la page Wiki n'est pas trouvée.
     */
    @Override
    public WikiEntity update(Long id, WikiEntity updater) throws Exception {
        Optional<WikiEntity> eOptional = repository.findById(id);
        if (eOptional.isPresent()) {
            WikiEntity wikiEntity = eOptional.get();
            wikiEntity.setTitle(updater.getTitle());
            wikiEntity.setDescription(updater.getDescription());
            wikiEntity.setImage(updater.getImage());
            return repository.save(wikiEntity);
        }
        throw new Exception("Could not find the Wiki page with id: " + id);
    }
}
