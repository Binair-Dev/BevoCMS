package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.RuleEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création de règle.
 *
 * @author Brian Van Bellinghen
 */
@Data
public class RuleForm {
    /**
     * Le titre de la règle.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * La description de la règle.
     */
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;

    /**
     * Convertit le formulaire en une entité RuleEntity.
     *
     * @return Une instance de RuleEntity basée sur les données du formulaire.
     */
    public RuleEntity toEntity() {
        RuleEntity entity = new RuleEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        return entity;
    }
}

