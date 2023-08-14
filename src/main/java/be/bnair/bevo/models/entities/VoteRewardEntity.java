package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.enums.EnumRewardType;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private EnumRewardType rewardType;

    private String command;
    private double credit;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private ServerEntity server;

}
