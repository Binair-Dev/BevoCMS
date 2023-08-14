package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bevo_shop_transactions")
@Data
public class ShopTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private ShopItemEntity item;

    private double credit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
