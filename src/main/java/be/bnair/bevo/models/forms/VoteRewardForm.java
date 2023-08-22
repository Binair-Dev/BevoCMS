package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import be.bnair.bevo.utils.constraints.TypeCheckConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@TypeCheckConstraint(message = "L'un des deux éléments en fonction du type n'est pas bon (Commande/Crédit).")
public class VoteRewardForm {
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le titre doit contenir entre 3 et 16 caractères.")
    private String title;
    @Min(value = 1, message = "Le pourcentage ne peut pas être vide")
    private double percent;
    @NotEmpty(message = "Le type de récompense ne peut pas être vide.")
    @Size(min = 3, max = 16, message = "Le type de récompense doit contenir entre 3 et 16 caractères.")
    private String rewardType;
    private String command;
    private double credit;
    @Min(value = 1, message = "Le serveur ne peut pas être vide")
    private Long server;

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
