package be.bnair.bevo.models.entities;

import java.time.LocalDate;
import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des nouvelles dans le système.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_news")
@Data
public class NewsEntity {
    /**
     * L'identifiant unique de la nouvelle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Le titre de la nouvelle.
     */
    private String title;

    /**
     * La description de la nouvelle.
     */
    private String description;

    /**
     * Le chemin de l'image associée à la nouvelle.
     */
    private String image;

    /**
     * La date de publication de la nouvelle.
     */
    private LocalDate date;

    /**
     * Indique si la nouvelle a été publiée.
     */
    private boolean isPublished;

    /**
     * L'auteur de la nouvelle.
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;
}
