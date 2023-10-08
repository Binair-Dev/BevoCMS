package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.repository.NewsRepository;
import be.bnair.bevo.services.NewsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des actualités de l'application Bevo.
 * Cette classe implémente l'interface {@link NewsService} et fournit les opérations de gestion des actualités.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;

    /**
     * Constructeur de la classe NewsServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des actualités.
     */
    public NewsServiceImpl(NewsRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée une nouvelle actualité.
     *
     * @param creater L'objet {@link NewsEntity} à créer.
     * @return L'actualité créée.
     */
    @Override
    public NewsEntity create(NewsEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les actualités.
     *
     * @return Une liste d'objets {@link NewsEntity}.
     */
    @Override
    public List<NewsEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une actualité par son identifiant.
     *
     * @param id L'identifiant de l'actualité à récupérer.
     * @return L'actualité correspondante, ou {@link Optional#empty()} si aucune actualité n'est trouvée.
     */
    @Override
    public Optional<NewsEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime une actualité par son identifiant.
     *
     * @param id L'identifiant de l'actualité à supprimer.
     * @throws Exception Si l'actualité avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour une actualité existante.
     *
     * @param id      L'identifiant de l'actualité à mettre à jour.
     * @param updater L'objet {@link NewsEntity} contenant les données de mise à jour.
     * @return L'actualité mise à jour.
     * @throws Exception Si l'actualité avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public NewsEntity update(Long id, NewsEntity updater) throws Exception {
        Optional<NewsEntity> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            NewsEntity newsEntity = entity.get();
            newsEntity.setTitle(updater.getTitle());
            newsEntity.setDescription(updater.getDescription());
            newsEntity.setImage(updater.getImage());
            newsEntity.setDate(LocalDate.now());
            newsEntity.setPublished(updater.isPublished());
            newsEntity.setAuthor(updater.getAuthor());
            return this.repository.save(newsEntity);
        }
        throw new Exception("Impossible de trouver l'actualité avec l'identifiant : " + id);
    }
}
