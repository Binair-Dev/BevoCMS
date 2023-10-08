package be.bnair.bevo.models.dto;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente un objet de transfert de données (DTO) pour les informations sur les transactions.
 *
 * @author Brian Van Bellinghen
 */
@Data
@Builder
public class TransactionDTO {
    /**
     * L'identifiant unique de la transaction.
     */
    private Long id;

    /**
     * Le montant de crédit associé à la transaction.
     */
    private double credit;

    /**
     * Le prix de la transaction.
     */
    private double price;

    /**
     * La date de la transaction.
     */
    private LocalDate date;

    /**
     * L'identifiant de l'utilisateur lié à la transaction.
     */
    private long user_id;

    /**
     * L'identifiant unique de la transaction.
     */
    private String identifier;

    /**
     * Convertit une entité TransactionEntity en un objet TransactionDTO.
     *
     * @param entity L'entité TransactionEntity à convertir en DTO.
     * @return Un objet TransactionDTO correspondant à l'entité.
     * @throws IllegalArgumentException si l'entité est nulle.
     */
    public static TransactionDTO toDTO(TransactionEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Ne peut être null");

        return TransactionDTO.builder()
                .id(entity.getId())
                .credit(entity.getCredit())
                .price(entity.getPrice())
                .date(entity.getDate())
                .user_id(entity.getUser().getId())
                .identifier(entity.getIdentifier())
                .build();
    }
}
