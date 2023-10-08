package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Cette classe représente une entité pour stocker les rangs des utilisateurs dans le système.
 *
 * @author Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_ranks")
@Data
public class RankEntity {
    /**
     * L'identifiant unique du rang.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Le titre du rang.
     */
    private String title;

    /**
     * La puissance associée au rang.
     */
    private Long power;
}
