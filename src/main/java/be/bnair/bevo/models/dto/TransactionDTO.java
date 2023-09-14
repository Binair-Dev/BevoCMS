package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private Long id;
    private double credit;
    private double price;
    private LocalDate date;
    private long user_id;
    private String identifier;

    public static TransactionDTO toDTO(TransactionEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return TransactionDTO.builder()
                .id(entity.getId())
                .credit(entity.getCredit())
                .price(entity.getPrice())
                .date(entity.getDate())
                .user_id(entity.getUser().getId())
                .identifier(entity.getIdentifier())
                .build();
    }
}