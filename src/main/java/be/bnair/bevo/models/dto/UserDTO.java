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

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les utilisateurs.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
@Builder
public class UserDTO {
    /**
     * L'identifiant unique de l'utilisateur.
     */
    private Long id;

    /**
     * Le pseudonyme de l'utilisateur.
     */
    private String nickname;

    /**
     * L'adresse e-mail de l'utilisateur.
     */
    private String email;

    /**
     * Indique si l'utilisateur est confirmé ou non.
     */
    private boolean isConfirmed;

    /**
     * Le rang (RankEntity) de l'utilisateur.
     */
    private RankEntity rank;

    /**
     * La date de création de l'utilisateur.
     */
    private LocalDate createdAt;

    /**
     * La date de mise à jour des informations de l'utilisateur.
     */
    private LocalDate updatedAt;

    /**
     * Le solde de crédit de l'utilisateur.
     */
    private double credit;

    /**
     * Convertit une entité UserEntity en un objet UserDTO.
     *
     * @param entity L'entité UserEntity à convertir en DTO.
     * @return Un objet UserDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static UserDTO toDTO(UserEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

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
