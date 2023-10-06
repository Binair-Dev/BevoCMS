package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.VoteEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Cette classe représente un formulaire de vote.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class VoteForm {
    /**
     * L'identifiant de l'utilisateur qui vote.
     */
    @Min(value = 1, message = "L'utilisateur ne peut pas être vide")
    private Long user;

    /**
     * Convertit ce formulaire en une entité Vote.
     *
     * @return L'entité Vote créée à partir de ce formulaire.
     */
    public VoteEntity toEntity() {
        VoteEntity entity = new VoteEntity();
        entity.setDate(LocalDate.now());
        return entity;
    }
}
