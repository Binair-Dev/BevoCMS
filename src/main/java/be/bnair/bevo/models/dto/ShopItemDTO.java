package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les articles de la boutique.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
@Builder
public class ShopItemDTO {
    /**
     * L'identifiant unique de l'article de la boutique.
     */
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
    private String content_image;

    /**
     * Le prix de l'article de la boutique.
     */
    private double price;

    /**
     * La commande associée à l'article de la boutique.
     */
    private String command;

    /**
     * L'identifiant de la catégorie de la boutique à laquelle appartient cet article.
     */
    private Long shop_category_id;

    /**
     * Le nom du serveur associé à cet article de la boutique.
     */
    private String server_name;

    /**
     * Convertit une entité ShopItemEntity en un objet ShopItemDTO.
     *
     * @param entity L'entité ShopItemEntity à convertir en DTO.
     * @return Un objet ShopItemDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static ShopItemDTO toDTO(ShopItemEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return ShopItemDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .price(entity.getPrice())
                .command(entity.getCommand())
                .shop_category_id(entity.getShopCategory().getId())
                .server_name(entity.getServer().getTitle())
                .content_image(entity.getContentImage())
                .build();
    }
}
