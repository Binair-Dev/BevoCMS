package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.repository.ShopItemRepository;
import be.bnair.bevo.services.ShopItemService;

import java.util.List;
import java.util.Optional;

@Service
public class ShopItemServiceImpl implements ShopItemService {
    private final ShopItemRepository repository;

    public ShopItemServiceImpl(ShopItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShopItemEntity create( ShopItemEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< ShopItemEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< ShopItemEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

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
        throw new Exception("Could not find the shop item with id: " + id);
    }
}