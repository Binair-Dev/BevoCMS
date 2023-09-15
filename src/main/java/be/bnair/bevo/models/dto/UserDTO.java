package be.bnair.bevo.models.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.services.ShopTransactionService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String nickname;
    private String email;
    private boolean isConfirmed;
    private RankEntity rank;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private double credit;

    public static UserDTO toDTO(UserEntity entity) {
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return UserDTO.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .isConfirmed(entity.isConfirmed())
                .rank(entity.getRank())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .credit(entity.getCredit())
                .build();
    }
}