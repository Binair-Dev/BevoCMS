package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.NewsEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewsForm {
    @NotNull @NotEmpty
    private String title;
    @NotNull @NotEmpty
    private String description;
    @NotNull @NotEmpty
    private String image;

    public NewsEntity toEntity() {
        NewsEntity entity = new NewsEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setImage(image);
        return entity;
    }
}
