package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.repository.ShopCategoryRepository;
import be.bnair.bevo.repository.ShopItemRepository;
import be.bnair.bevo.services.ShopCategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryRepository repository;
    private final ShopItemRepository shopItemRepository;

    public ShopCategoryServiceImpl(ShopCategoryRepository repository, ShopItemRepository shopItemRepository) {
        this.repository = repository;
        this.shopItemRepository = shopItemRepository;
    }

    @Override
    public ShopCategoryEntity create( ShopCategoryEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< ShopCategoryEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< ShopCategoryEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        Optional<ShopCategoryEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            ShopCategoryEntity shopCategoryEntity = entity.get();
            for (ShopItemEntity shopItemEntity : shopCategoryEntity.getShopItems()) {
                shopItemRepository.deleteById(shopItemEntity.getId());
            }
        }
        repository.deleteById(id);
    }

    @Override
    public ShopCategoryEntity update(Long id, ShopCategoryEntity updater) throws Exception {
        Optional<ShopCategoryEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            ShopCategoryEntity shopCategoryEntity = entity.get();
            shopCategoryEntity.setTitle(updater.getTitle());
            shopCategoryEntity.setDisplayOrder(updater.getDisplayOrder());
            shopCategoryEntity.setShopItems(updater.getShopItems());
            return repository.save(shopCategoryEntity);
        }
        throw new Exception("Could not find the shop category with id: " + id);
    }
}