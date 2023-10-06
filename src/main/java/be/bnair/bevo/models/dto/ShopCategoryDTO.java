package be.bnair.bevo.models.dto;

import java.util.List;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les catégories de la boutique.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
@Builder
public class ShopCategoryDTO {
    /**
     * L'identifiant unique de la catégorie de la boutique.
     */
    private Long id;

    /**
     * Le titre ou nom de la catégorie de la boutique.
     */
    private String title;

    /**
     * L'ordre d'affichage de la catégorie de la boutique.
     */
    private int displayOrder;

    /**
     * La liste des articles de la boutique associés à cette catégorie.
     */
    private List<ShopItemDTO> shopItems;

    /**
     * Convertit une entité ShopCategoryEntity en un objet ShopCategoryDTO.
     *
     * @param entity L'entité ShopCategoryEntity à convertir en DTO.
     * @param items  La liste des objets ShopItemDTO associés à la catégorie.
     * @return Un objet ShopCategoryDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static ShopCategoryDTO toDTO(ShopCategoryEntity entity, List<ShopItemDTO> items) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return ShopCategoryDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .displayOrder(entity.getDisplayOrder())
                .shopItems(items)
                .build();
    }
}
