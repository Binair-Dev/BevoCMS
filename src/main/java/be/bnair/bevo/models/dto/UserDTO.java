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
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String nickname;
    private String email;
    private boolean isConfirmed;
    private List<NewsEntity> news;
    private List<TransactionEntity> transactions;
    private List<VoteRewardEntity> voteRewards = new ArrayList<>();
    private RankEntity rank;
    private List<ShopTransactionEntity> shopTransactions;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static UserDTO toDTO(UserEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return UserDTO.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .isConfirmed(entity.isConfirmed())
                .news(entity.getNews().stream().toList())
                .transactions(entity.getTransactions().stream().toList())
                .voteRewards(entity.getVoteRewards())
                .rank(entity.getRank())
                .shopTransactions(entity.getShopTransactions().stream().toList())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}