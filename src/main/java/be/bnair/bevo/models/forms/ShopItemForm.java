package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShopItemForm {
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;
    @NotEmpty(message = "L'url de l'image ne peut pas être vide.")
    @Size(min = 3, message = "L'url de l'image doit contenir au moins 3 caractères")
    private String image;
    @Min(value = 1, message = "Les crédits donnés ne peuvent pas être vide")
    private double price;
    @NotEmpty(message = "La commande ne peut pas être vide.")
    @Size(min = 3, message = "La commande doit contenir au moins 3 caractères")
    private String command;
    @Min(value = 1, message = "La catégorie du shop ne peuvent pas être vide")
    private Long shopCategory;
    @Min(value = 1, message = "Le serveur ne peuvent pas être vide")
    private Long server;

    public ShopItemEntity toEntity() {
        ShopItemEntity shopItemEntity = new ShopItemEntity();
        shopItemEntity.setTitle(title);
        shopItemEntity.setDescription(description);
        shopItemEntity.setImage(image);
        shopItemEntity.setPrice(price);
        shopItemEntity.setCommand(command);        
        return shopItemEntity;
    }
}
