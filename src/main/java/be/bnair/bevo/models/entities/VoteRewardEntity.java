package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.EnumRewardType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

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
