package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RankEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankDTO {
    private Long id;
    private String title;
    private Long power;

    public static RankDTO toDTO(RankEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return RankDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .power(entity.getPower())
                .build();
    }
}