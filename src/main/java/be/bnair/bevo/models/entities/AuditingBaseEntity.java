package be.bnair.bevo.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/**
 * Cette classe abstraite sert de base pour toutes les entités qui nécessitent un suivi de l'audition des données.
 *
 * @author Brian Van Bellinghen
 */
@MappedSuperclass
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class AuditingBaseEntity {
    /**
     * L'identifiant unique de l'entité.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * La date de création de l'entité.
     */
    @CreatedDate
    private LocalDate createdAt;

    /**
     * La date de la dernière modification de l'entité.
     */
    @LastModifiedDate
    private LocalDate updatedAt;
}
