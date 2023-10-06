package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.NewsEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création d'article de presse.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
public class NewsForm {
    /**
     * Le titre de l'article de presse.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * La description de l'article de presse.
     */
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;

    /**
     * L'URL de l'image associée à l'article de presse.
     */
    @NotEmpty(message = "L'URL de l'image ne peut pas être vide.")
    @Size(min = 3, message = "L'URL de l'image doit contenir au moins 3 caractères")
    private String image;

    /**
     * Convertit le formulaire en une entité NewsEntity.
     *
     * @return Une instance de NewsEntity basée sur les données du formulaire.
     */
    public NewsEntity toEntity() {
        NewsEntity entity = new NewsEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setImage(image);
        return entity;
    }
}
