package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.VoteEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class VoteForm {
    @Min(value = 1, message = "L'utilisateur ne peut pas être vide")
    private Long user;

    public VoteEntity toEntity() {
        VoteEntity entity = new VoteEntity();
        entity.setDate(LocalDate.now());
        return entity;
    }
}
