package be.bnair.bevo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des articles de la boutique dans le système.
 *
 * @author Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_shop_items")
@Data
public class ShopItemEntity {
    /**
     * L'identifiant unique de l'article de la boutique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Le titre de l'article de la boutique.
     */
    private String title;

    /**
     * La description de l'article de la boutique.
     */
    private String description;

    /**
     * Le chemin de l'image associée à l'article de la boutique.
     */
    private String image;

    /**
     * Le chemin de l'image de contenu associée à l'article de la boutique.
     */
    private String contentImage;

    /**
     * Le prix de l'article de la boutique.
     */
    private double price;

    /**
     * La commande associée à l'article de la boutique.
     */
    private String command;

    /**
     * La catégorie de la boutique à laquelle appartient cet article.
     */
    @ManyToOne
    @JoinColumn(name = "shop_category_id")
    private ShopCategoryEntity shopCategory;

    /**
     * Le serveur associé à cet article de la boutique.
     */
    @ManyToOne
    @JoinColumn(name = "server_id")
    private ServerEntity server;
}
