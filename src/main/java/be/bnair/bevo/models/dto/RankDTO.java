package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RankEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les rangs.
 *
 * @author Brian Van Bellinghen
 */
@Data
@Builder
public class RankDTO {
    /**
     * L'identifiant unique du rang.
     */
    private Long id;

    /**
     * Le titre du rang.
     */
    private String title;

    /**
     * La puissance du rang.
     */
    private Long power;

    /**
     * Convertit une entité RankEntity en un objet RankDTO.
     *
     * @param entity L'entité RankEntity à convertir en DTO.
     * @return Un objet RankDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static RankDTO toDTO(RankEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return RankDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .power(entity.getPower())
                .build();
    }
}
