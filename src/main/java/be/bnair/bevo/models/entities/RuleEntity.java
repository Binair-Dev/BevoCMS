package be.bnair.bevo.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker les règles dans le système.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_ruless")
@Data
public class RuleEntity {
    /**
     * L'identifiant unique de la règle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre de la règle.
     */
    private String title;

    /**
     * La description de la règle.
     */
    private String description;
}
