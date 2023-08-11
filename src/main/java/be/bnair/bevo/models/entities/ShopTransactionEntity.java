package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bevo_shop_transactions")
@Data
public class ShopTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity user;
    
    @OneToOne
    private ShopItemEntity item;

    private double credit;
}
