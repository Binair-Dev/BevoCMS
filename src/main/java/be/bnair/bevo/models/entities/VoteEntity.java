package be.bnair.bevo.models.entities;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bevo_votes")
@Data
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
