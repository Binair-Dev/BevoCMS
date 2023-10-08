package be.bnair.bevo.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des offres PayPal dans le système.
 *
 * @author Brian Van Bellinghen
 */
@Entity
@Table(name = "bevo_paypal_offers")
@Data
public class PaypalOfferEntity {
    /**
     * L'identifiant unique de l'offre PayPal.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre de l'offre PayPal.
     */
    private String title;

    /**
     * La description de l'offre PayPal.
     */
    private String description;

    /**
     * Le prix de l'offre PayPal.
     */
    private double price;

    /**
     * Le crédit associé à l'offre PayPal.
     */
    private double credit;
}
