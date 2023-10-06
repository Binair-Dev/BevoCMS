package be.bnair.bevo.models.dto;

import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les transactions de la boutique.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
@Builder
public class ShopTransactionDTO {
    /**
     * L'identifiant unique de la transaction de la boutique.
     */
    private Long id;

    /**
     * L'objet ShopItemDTO associé à la transaction.
     */
    private ShopItemDTO item;

    /**
     * Le montant de crédit associé à la transaction.
     */
    private double credit;

    /**
     * Le nom d'utilisateur de l'utilisateur lié à la transaction.
     */
    private String user_name;

    /**
     * Convertit une entité ShopTransactionEntity en un objet ShopTransactionDTO.
     *
     * @param entity L'entité ShopTransactionEntity à convertir en DTO.
     * @return Un objet ShopTransactionDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static ShopTransactionDTO toDTO(ShopTransactionEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return ShopTransactionDTO.builder()
                .id(entity.getId())
                .item(ShopItemDTO.toDTO(entity.getItem()))
                .credit(entity.getCredit())
                .user_name(entity.getUser().getNickname())
                .build();
    }
}
