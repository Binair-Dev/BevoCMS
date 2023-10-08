package be.bnair.bevo.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des informations wiki dans le système.
 *
 * @author Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_wikis")
@Data
public class WikiEntity {
    /**
     * L'identifiant unique de l'information wiki.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre de l'information wiki.
     */
    private String title;

    /**
     * La description de l'information wiki.
     */
    private String description;

    /**
     * Le chemin de l'image associée à l'information wiki.
     */
    private String image;
}
