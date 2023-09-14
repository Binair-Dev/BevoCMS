package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RankEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RankForm {
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    @Min(value = 1, message = "Le power donnés ne peut pas être vide")
    private int power;

    public RankEntity toEntity() {
        RankEntity entity = new RankEntity();
        entity.setTitle(title);
        entity.setPower(Long.valueOf(power));
        return entity;
    }
}
