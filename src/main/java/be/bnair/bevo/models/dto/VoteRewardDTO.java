package be.bnair.bevo.models.dto;

import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les récompenses de vote.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
@Builder
public class VoteRewardDTO {
    /**
     * L'identifiant unique de la récompense de vote.
     */
    private Long id;

    /**
     * Le titre de la récompense de vote.
     */
    private String title;

    /**
     * Le pourcentage de la récompense de vote.
     */
    private double percent;

    /**
     * Le type de récompense de vote (EnumRewardType).
     */
    private EnumRewardType rewardType;

    /**
     * La commande associée à la récompense de vote.
     */
    private String command;

    /**
     * Le montant de crédit associé à la récompense de vote.
     */
    private double credit;

    /**
     * L'identifiant du serveur associé à la récompense de vote.
     */
    private Long server;

    /**
     * Convertit une entité VoteRewardEntity en un objet VoteRewardDTO.
     *
     * @param entity L'entité VoteRewardEntity à convertir en DTO.
     * @return Un objet VoteRewardDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static VoteRewardDTO toDTO(VoteRewardEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return VoteRewardDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .percent(entity.getPercent())
                .rewardType(entity.getRewardType())
                .command(entity.getCommand())
                .credit(entity.getCredit())
                .server(entity.getServer().getId())
                .build();
    }
}
