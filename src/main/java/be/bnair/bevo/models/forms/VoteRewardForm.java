package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import be.bnair.bevo.utils.constraints.TypeCheckConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de récompense de vote.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
@TypeCheckConstraint(message = "L'un des deux éléments en fonction du type n'est pas bon (Commande/Crédit).")
public class VoteRewardForm {
    /**
     * Le titre de la récompense.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le titre doit contenir entre 3 et 16 caractères.")
    private String title;

    /**
     * Le pourcentage de la récompense.
     */
    @Min(value = 1, message = "Le pourcentage ne peut pas être vide")
    private double percent;

    /**
     * Le type de récompense (Commande ou Crédit).
     */
    @NotEmpty(message = "Le type de récompense ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le type de récompense doit contenir entre 3 et 16 caractères.")
    private String rewardType;

    /**
     * La commande associée à la récompense (si le type est "Commande").
     */
    private String command;

    /**
     * Le montant de crédit associé à la récompense (si le type est "Crédit").
     */
    private double credit;

    /**
     * L'identifiant du serveur associé à la récompense.
     */
    @Min(value = 1, message = "Le serveur ne peut pas être vide")
    private Long server;

    /**
     * Convertit ce formulaire en une entité VoteReward.
     *
     * @return L'entité VoteReward créée à partir de ce formulaire.
     */
    public VoteRewardEntity toEntity() {
        VoteRewardEntity voteRewardEntity = new VoteRewardEntity();
        voteRewardEntity.setTitle(title);
        voteRewardEntity.setPercent(percent);
        voteRewardEntity.setRewardType(EnumRewardType.valueOf(rewardType));
        voteRewardEntity.setCommand(command);
        voteRewardEntity.setCredit(credit);
        return voteRewardEntity;
    }
}
