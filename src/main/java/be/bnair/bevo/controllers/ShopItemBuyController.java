package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.*;
import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import be.bnair.bevo.utils.MCUtils;
import be.bnair.bevo.utils.RconUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Contrôleur pour la gestion de l'achat d'articles de boutique.
 * Ce contrôleur permet aux utilisateurs d'acheter des articles de boutique.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = "/shop")
public class ShopItemBuyController {
    private final UserService userService;
    private final ShopItemService shopItemService;
    private final ShopTransactionService shopTransactionService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du contrôleur d'achat d'articles de boutique.
     *
     * @param jwtUtil                Le service JWT pour l'authentification des utilisateurs.
     * @param userService            Le service de gestion des utilisateurs.
     * @param shopTransactionService Le service de gestion des transactions de boutique.
     * @param shopItemService        Le service de gestion des articles de boutique.
     */
    public ShopItemBuyController(JwtUtil jwtUtil, UserService userService, ShopTransactionService shopTransactionService, ShopItemService shopItemService) {
        this.userService = userService;
        this.shopTransactionService = shopTransactionService;
        this.jwtUtil = jwtUtil;
        this.shopItemService = shopItemService;
    }

    /**
     * Permet à un utilisateur d'acheter un article de boutique.
     *
     * @param request     La requête HTTP.
     * @param shopItemId  L'identifiant de l'article de boutique à acheter.
     * @return Une réponse HTTP indiquant le résultat de l'achat de l'article de boutique.
     * @throws IOException Une exception en cas d'erreur d'entrée/sortie.
     */
    @GetMapping("/buy")
    public ResponseEntity<MessageResponse> buyItem(HttpServletRequest request, @RequestParam String shopItemId) throws IOException {
        if(!shopItemId.isEmpty()) {
            UserEntity ue = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
            if(ue == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Not authorized"));

            Optional<ShopItemEntity> shopItemEntityOptional = shopItemService.getOneById(Long.valueOf(shopItemId));
            if(shopItemEntityOptional.isPresent()) {
                ShopItemEntity shopItemEntity = shopItemEntityOptional.get();
                ServerEntity serverEntity = shopItemEntity.getServer();

                if(ue.getCredit() < shopItemEntity.getPrice()) return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Tu n'as pas assez de crédit disponible !"));

                if(MCUtils.isOnline(serverEntity.getServerIp(), serverEntity.getServerPort())) {
                    try {
                        ShopTransactionEntity shopTransactionEntity = new ShopTransactionEntity();
                        shopTransactionEntity.setItem(shopItemEntity);
                        shopTransactionEntity.setCredit(shopItemEntity.getPrice());
                        shopTransactionEntity.setUser(ue);
                        shopTransactionService.create(shopTransactionEntity);

                        ue.setCredit(ue.getCredit() - shopItemEntity.getPrice());
                        userService.update(ue.getId(), ue);

                        RconUtils.sendCommandToServer(serverEntity, shopItemEntity.getCommand().replaceAll("%player%", ue.getUsername()));
                        return ResponseEntity.accepted().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'article a bien été acheté !"));
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Une erreur est survenue lors de votre achat! Veuillez contacter un adimistrateur!"));
                    }
                }
                return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le serveur n'est pas en ligne !"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'article avec l'id " + shopItemId + " n'existe pas !"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Aucun article sélectionné !"));
    }
}
