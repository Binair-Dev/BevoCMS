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

@Data
@Builder
public class ShopItemDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String content_image;
    private double price;
    private String command;
    private Long shop_category_id;
    private String server_name;

    public static ShopItemDTO toDTO(ShopItemEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

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