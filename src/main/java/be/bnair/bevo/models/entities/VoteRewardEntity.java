package be.bnair.bevo.models.entities;

import be.bnair.bevo.utils.EnumRewardType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bevo_vote_rewards")
@Data
public class VoteRewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double percent;

    @OneToOne
    private ServerEntity server;

    @Enumerated(EnumType.STRING)
    private EnumRewardType rewardType;

    private String command;
    private double credit;
}
