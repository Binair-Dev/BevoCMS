package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.VoteEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les votes.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
@Builder
public class VoteDTO {
    /**
     * L'identifiant unique du vote.
     */
    private Long id;

    /**
     * La date du vote.
     */
    private LocalDate date;

    /**
     * L'identifiant de l'utilisateur lié au vote.
     */
    private Long user_id;

    /**
     * Le nom d'utilisateur de l'utilisateur lié au vote.
     */
    private String user_name;

    /**
     * Convertit une entité VoteEntity en un objet VoteDTO.
     *
     * @param entity L'entité VoteEntity à convertir en DTO.
     * @return Un objet VoteDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static VoteDTO toDTO(VoteEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return VoteDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .user_id(entity.getUser().getId())
                .user_name(entity.getUser().getNickname())
                .build();
    }
}
