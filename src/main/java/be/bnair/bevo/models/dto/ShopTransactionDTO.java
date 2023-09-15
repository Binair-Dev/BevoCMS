package be.bnair.bevo.models.dto;

import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ShopTransactionDTO {
    private Long id;
    private ShopItemDTO item;
    private double credit;
    private String user_name;

    public static ShopTransactionDTO toDTO(ShopTransactionEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return ShopTransactionDTO.builder()
                .id(entity.getId())
                .item(ShopItemDTO.toDTO(entity.getItem()))
                .credit(entity.getCredit())
                .user_name(entity.getUser().getNickname())
                .build();
    }
}