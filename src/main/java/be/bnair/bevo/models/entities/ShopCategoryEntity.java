package be.bnair.bevo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "bevo_shop_categories")
@Data
public class ShopCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int displayOrder;

    @OneToMany(mappedBy = "shopCategory", orphanRemoval = true)
    private Set<ShopItemEntity> shopItems = new LinkedHashSet<>();

    public void addShopItem(ShopItemEntity item) {
        if(!shopItems.contains(item))
            this.shopItems.add(item);
    }
}
