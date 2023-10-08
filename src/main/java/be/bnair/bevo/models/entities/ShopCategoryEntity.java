package be.bnair.bevo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Cette classe représente une entité pour stocker des catégories de produits dans le système de boutique.
 *
 * @author Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_shop_categories")
@Data
public class ShopCategoryEntity {
    /**
     * L'identifiant unique de la catégorie de produit.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Le titre ou nom de la catégorie de produit.
     */
    private String title;

    /**
     * L'ordre d'affichage de la catégorie de produit.
     */
    private int displayOrder;
}
