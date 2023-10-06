package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RankEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création de grade.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class RankForm {
    /**
     * Le titre du grade.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * La puissance (power) associée au grade.
     */
    @Min(value = 1, message = "La puissance donnée ne peut pas être vide")
    private int power;

    /**
     * Convertit le formulaire en une entité RankEntity.
     *
     * @return Une instance de RankEntity basée sur les données du formulaire.
     */
    public RankEntity toEntity() {
        RankEntity entity = new RankEntity();
        entity.setTitle(title);
        entity.setPower(Long.valueOf(power));
        return entity;
    }
}
