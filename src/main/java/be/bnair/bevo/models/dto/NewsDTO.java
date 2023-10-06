package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les actualités.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
@Builder
public class NewsDTO {
    /**
     * L'identifiant unique de l'actualité.
     */
    private Long id;

    /**
     * Le titre de l'actualité.
     */
    private String title;

    /**
     * La description de l'actualité.
     */
    private String description;

    /**
     * Le chemin de l'image associée à l'actualité.
     */
    private String image;

    /**
     * La date de l'actualité.
     */
    private LocalDate date;

    /**
     * Le nom d'utilisateur de l'auteur de l'actualité.
     */
    private String userName;

    /**
     * Convertit une entité NewsEntity en un objet NewsDTO.
     *
     * @param entity L'entité NewsEntity à convertir en DTO.
     * @return Un objet NewsDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static NewsDTO toDTO(NewsEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

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
