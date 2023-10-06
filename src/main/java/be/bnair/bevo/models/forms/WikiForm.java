package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.WikiEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire pour créer ou mettre à jour une entrée de Wiki.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class WikiForm {
    /**
     * Le titre de l'entrée de Wiki.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * La description de l'entrée de Wiki.
     */
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;

    /**
     * L'URL de l'image associée à l'entrée de Wiki.
     */
    @NotEmpty(message = "L'URL de l'image ne peut pas être vide.")
    @Size(min = 3, message = "L'URL de l'image doit contenir au moins 3 caractères")
    private String image;

    /**
     * Convertit ce formulaire en une entité Wiki.
     *
     * @return L'entité Wiki créée à partir de ce formulaire.
     */
    public WikiEntity toEntity() {
        WikiEntity entity = new WikiEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setImage(image);
        return entity;
    }
}
