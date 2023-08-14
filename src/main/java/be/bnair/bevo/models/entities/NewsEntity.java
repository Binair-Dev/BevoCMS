package be.bnair.bevo.models.entities;

import java.time.LocalDate;
import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bevo_news")
@Data
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String image;
    private LocalDate date;
    private boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;
}
