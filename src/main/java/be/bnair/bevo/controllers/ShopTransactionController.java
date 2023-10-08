package be.bnair.bevo.controllers;

import be.bnair.bevo.models.dto.ShopItemDTO;
import be.bnair.bevo.models.dto.ShopTransactionDTO;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.ShopTransactionForm;
import be.bnair.bevo.services.ShopItemService;
import be.bnair.bevo.services.ShopTransactionService;
import be.bnair.bevo.services.UserService;

import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des transactions de boutique.
 * Ce contrôleur permet de gérer les opérations de création et de récupération
 * des transactions de boutique ainsi que l'historique des transactions pour un utilisateur.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/shop-transactions"})
public class ShopTransactionController {
    private final ShopTransactionService shopTransactionService;
    private final UserService userService;
    private final ShopItemService shopItemService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du contrôleur des transactions de boutique.
     *
     * @param shopTransactionService Le service de gestion des transactions de boutique.
     * @param userService            Le service de gestion des utilisateurs.
     * @param shopItemService        Le service de gestion des articles de boutique.
     * @param jwtUtil                Le service JWT pour l'authentification des utilisateurs.
     */
    public ShopTransactionController(ShopTransactionService shopTransactionService, UserService userService, ShopItemService shopItemService, JwtUtil jwtUtil) {
        this.shopTransactionService = shopTransactionService;
        this.userService = userService;
        this.shopItemService = shopItemService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Crée une nouvelle transaction de boutique.
     *
     * @param shopTransactionForm Les données de la transaction de boutique à créer.
     * @param bindingResult       Le résultat de la validation des données de la requête.
     * @return Une réponse HTTP indiquant le résultat de la création de la transaction de boutique.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid ShopTransactionForm shopTransactionForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }
        
        Optional<ShopItemEntity> shopItemEntityOptional = this.shopItemService.getOneById(shopTransactionForm.getItem());
        Optional<UserEntity> userEntityOptional = this.userService.getOneById(shopTransactionForm.getUser());
        if(shopItemEntityOptional.isPresent() && userEntityOptional.isPresent()) {
            ShopTransactionEntity shopTransactionEntity = shopTransactionForm.toEntity();
            shopTransactionEntity.setItem(shopItemEntityOptional.get());
            shopTransactionEntity.setUser(userEntityOptional.get());
            this.shopTransactionService.create(shopTransactionEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La transaction shop a bien été créée."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de créer la transaction shop."));
    }

    /**
     * Récupère l'historique des transactions de boutique pour un utilisateur donné.
     *
     * @param request La requête HTTP.
     * @return Une réponse HTTP contenant l'historique des transactions de boutique de l'utilisateur.
     */
    @GetMapping(path = {"/history"})
    public ResponseEntity<List<ShopTransactionDTO>> findByUserId(HttpServletRequest request) {
        UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());

        List<ShopTransactionDTO> shopTransaction = this.shopTransactionService.getAll().stream().filter(t -> t.getUser().getId() == userDetails.getId()).map(ShopTransactionDTO::toDTO).toList();
        return ResponseEntity.ok(shopTransaction);
    }
}