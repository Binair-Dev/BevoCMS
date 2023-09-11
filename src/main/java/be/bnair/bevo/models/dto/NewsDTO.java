package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private LocalDate date;
    private String userName;

    public static NewsDTO toDTO(NewsEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return NewsDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .date(entity.getDate())
                .userName(entity.getAuthor().getNickname())
                .build();
    }
}