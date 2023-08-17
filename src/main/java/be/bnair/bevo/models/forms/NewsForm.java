package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewsForm {
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;
    @NotEmpty(message = "L'url de l'image ne peut pas être vide.")
    @Size(min = 3, message = "L'url de l'image doit contenir au moins 3 caractères")
    private String image;

    public NewsEntity toEntity() {
        NewsEntity entity = new NewsEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setImage(image);
        return entity;
    }
}
