package be.bnair.bevo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bevo_shop_items")
@Data
public class ShopItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String image;
    private String contentImage;

    private double price;
    private String command;

    @ManyToOne
    @JoinColumn(name = "shop_category_id")
    private ShopCategoryEntity shopCategory;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private ServerEntity server;
}
