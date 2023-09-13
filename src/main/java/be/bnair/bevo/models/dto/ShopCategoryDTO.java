package be.bnair.bevo.models.dto;

import java.util.List;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopCategoryDTO {
    private Long id;
    private String title;
    private int displayOrder;
    private List<ShopItemDTO> shopItems;

    public static ShopCategoryDTO toDTO(ShopCategoryEntity entity, List<ShopItemDTO> items) {
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return ShopCategoryDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .displayOrder(entity.getDisplayOrder())
                .shopItems(items)
                .build();
    }
}