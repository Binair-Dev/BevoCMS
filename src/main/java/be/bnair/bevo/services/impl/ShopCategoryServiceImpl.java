package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.repository.ShopCategoryRepository;
import be.bnair.bevo.repository.ShopItemRepository;
import be.bnair.bevo.services.ShopCategoryService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des catégories de la boutique de l'application Bevo.
 * Cette classe implémente l'interface {@link ShopCategoryService} et fournit les opérations de gestion des catégories de la boutique.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryRepository repository;
    private final ShopItemRepository shopItemRepository;

    /**
     * Constructeur de la classe ShopCategoryServiceImpl.
     *
     * @param repository         Le repository permettant l'accès aux données des catégories de la boutique.
     * @param shopItemRepository Le repository permettant l'accès aux données des articles de la boutique.
     */
    public ShopCategoryServiceImpl(ShopCategoryRepository repository, ShopItemRepository shopItemRepository) {
        this.repository = repository;
        this.shopItemRepository = shopItemRepository;
    }

    /**
     * Crée une nouvelle catégorie de la boutique.
     *
     * @param creater L'objet {@link ShopCategoryEntity} à créer.
     * @return La catégorie de la boutique créée.
     */
    @Override
    public ShopCategoryEntity create(ShopCategoryEntity creater) {
        return repository.save(creater);
    }

    /**
     * Récupère la liste de toutes les catégories de la boutique.
     *
     * @return Une liste d'objets {@link ShopCategoryEntity}.
     */
    @Override
    public List<ShopCategoryEntity> getAll() {
        return repository.findAll();
    }

    /**
     * Récupère une catégorie de la boutique par son identifiant.
     *
     * @param id L'identifiant de la catégorie de la boutique à récupérer.
     * @return La catégorie de la boutique correspondante, ou {@link Optional#empty()} si aucune catégorie n'est trouvée.
     */
    @Override
    public Optional<ShopCategoryEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    /**
     * Supprime une catégorie de la boutique par son identifiant.
     *
     * @param id L'identifiant de la catégorie de la boutique à supprimer.
     * @throws Exception Si la catégorie de la boutique avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public void remove(Long id) throws Exception {
        Optional<ShopCategoryEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            ShopCategoryEntity shopCategoryEntity = entity.get();
            for (ShopItemEntity shopItemEntity : shopItemRepository.findAll()) {
                if (shopItemEntity.getShopCategory().getId() == id) {
                    shopItemRepository.deleteById(shopItemEntity.getId());
                }
            }
        }
        repository.deleteById(id);
    }

    /**
     * Met à jour une catégorie de la boutique existante.
     *
     * @param id      L'identifiant de la catégorie de la boutique à mettre à jour.
     * @param updater L'objet {@link ShopCategoryEntity} contenant les données de mise à jour.
     * @return La catégorie de la boutique mise à jour.
     * @throws Exception Si la catégorie de la boutique avec l'identifiant spécifié n'est pas trouvée.
     */
    @Override
    public ShopCategoryEntity update(Long id, ShopCategoryEntity updater) throws Exception {
        Optional<ShopCategoryEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            ShopCategoryEntity shopCategoryEntity = entity.get();
            shopCategoryEntity.setTitle(updater.getTitle());
            shopCategoryEntity.setDisplayOrder(updater.getDisplayOrder());
            return repository.save(shopCategoryEntity);
        }
        throw new Exception("Impossible de trouver la catégorie de la boutique avec l'identifiant : " + id);
    }
}
