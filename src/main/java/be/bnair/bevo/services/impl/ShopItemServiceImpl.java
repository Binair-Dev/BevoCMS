package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.repository.ShopItemRepository;
import be.bnair.bevo.services.ShopItemService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des articles de la boutique de l'application Bevo.
 * Cette classe implémente l'interface {@link ShopItemService} et fournit les opérations de gestion des articles de la boutique.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class ShopItemServiceImpl implements ShopItemService {
    private final ShopItemRepository repository;

    /**
     * Constructeur de la classe ShopItemServiceImpl.
     *
     * @param repository Le repository permettant l'accès aux données des articles de la boutique.
     */
    public ShopItemServiceImpl(ShopItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée un nouvel article de la boutique.
     *
     * @param creater L'objet {@link ShopItemEntity} à créer.
     * @return L'article de la boutique créé.
     */
    @Override
    public ShopItemEntity create(ShopItemEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de tous les articles de la boutique.
     *
     * @return Une liste d'objets {@link ShopItemEntity}.
     */
    @Override
    public List<ShopItemEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère un article de la boutique par son identifiant.
     *
     * @param id L'identifiant de l'article de la boutique à récupérer.
     * @return L'article de la boutique correspondant, ou {@link Optional#empty()} si aucun article n'est trouvé.
     */
    @Override
    public Optional<ShopItemEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime un article de la boutique par son identifiant.
     *
     * @param id L'identifiant de l'article de la boutique à supprimer.
     * @throws Exception Si l'article de la boutique avec l'identifiant spécifié n'est pas trouvé.
     */
    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    /**
     * Met à jour un article de la boutique existant.
     *
     * @param id      L'identifiant de l'article de la boutique à mettre à jour.
     * @param updater L'objet {@link ShopItemEntity} contenant les données de mise à jour.
     * @return L'article de la boutique mis à jour.
     * @throws Exception Si l'article de la boutique avec l'identifiant spécifié n'est pas trouvé.
     */
    @Override
    public ShopItemEntity update(Long id, ShopItemEntity updater) throws Exception {
        Optional<ShopItemEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            ShopItemEntity shopItemEntity = entity.get();
            shopItemEntity.setTitle(updater.getTitle());
            shopItemEntity.setDescription(updater.getDescription());
            shopItemEntity.setImage(updater.getImage());
            shopItemEntity.setPrice(updater.getPrice());
            shopItemEntity.setCommand(updater.getCommand());
            shopItemEntity.setShopCategory(updater.getShopCategory());
            shopItemEntity.setServer(updater.getServer());
            return repository.save(shopItemEntity);
        }
        throw new Exception("Impossible de trouver l'article de la boutique avec l'identifiant : " + id);
    }
}
