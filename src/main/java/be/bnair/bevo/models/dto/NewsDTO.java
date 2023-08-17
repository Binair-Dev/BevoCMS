package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String author;

    public static NewsDTO toDTO(NewsEntity entity){
        if(entity == null)
            throw  new IllegalArgumentException("Ne peut etre null");

        return NewsDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .date(entity.getDate())
                .author(entity.getAuthor().getUsername())
                .build();
    }
}