package be.bnair.bevo.models.dto;

import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteRewardDTO {
    private Long id;
    private String title;
    private double percent;
    private EnumRewardType rewardType;
    private String command;
    private double credit;
    private String server_id;

    public static VoteRewardDTO toDTO(VoteRewardEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return VoteRewardDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .percent(entity.getPercent())
                .rewardType(entity.getRewardType())
                .command(entity.getCommand())
                .build();
    }
}