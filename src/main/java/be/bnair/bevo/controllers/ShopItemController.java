package be.bnair.bevo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bnair.bevo.models.dto.ShopItemDTO;
import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.forms.ShopItemForm;
import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.ServerService;
import be.bnair.bevo.services.ShopCategoryService;
import be.bnair.bevo.services.ShopItemService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des articles de boutique.
 * Ce contrôleur permet de gérer les opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
 * sur les articles de boutique.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/shop-items"})
public class ShopItemController {
    private final ShopItemService shopItemService;
    private final ShopCategoryService shopCategoryService;
    private final ServerService serverService;

    /**
     * Constructeur du contrôleur des articles de boutique.
     *
     * @param shopItemService    Le service de gestion des articles de boutique.
     * @param shopCategoryService Le service de gestion des catégories de boutique.
     * @param serverService      Le service de gestion des serveurs.
     */
    public ShopItemController(ShopItemService shopItemService, ShopCategoryService shopCategoryService, ServerService serverService) {
        this.shopItemService = shopItemService;
        this.shopCategoryService = shopCategoryService;
        this.serverService = serverService;
    }

    /**
     * Met à jour un article de boutique existant.
     *
     * @param id           L'identifiant de l'article de boutique à mettre à jour.
     * @param shopItemForm Les données de l'article de boutique à mettre à jour.
     * @param bindingResult Le résultat de la validation des données de la requête.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour de l'article de boutique.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
        @PathVariable Long id,
        @RequestBody @Valid ShopItemForm shopItemForm,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<ShopItemEntity> optionalShopItemEntity = this.shopItemService.getOneById(id);
        Optional<ShopCategoryEntity> optionalShopCategory = this.shopCategoryService.getOneById(shopItemForm.getShopCategory());
        Optional<ServerEntity> optionalServer = this.serverService.getOneById(shopItemForm.getServer());
        if(optionalShopItemEntity.isPresent() && optionalShopCategory.isPresent() && optionalServer.isPresent()) {
            ShopItemEntity shopItemEntity = shopItemForm.toEntity();
            shopItemEntity.setShopCategory(optionalShopCategory.get());
            shopItemEntity.setServer(optionalServer.get());
            shopItemEntity.setId(optionalShopItemEntity.get().getId());
            try {
                this.shopItemService.update(id, shopItemEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "L'item du shop a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'item du shop a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'item du shop a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée un nouvel article de boutique.
     *
     * @param shopItemForm Les données de l'article de boutique à créer.
     * @param bindingResult Le résultat de la validation des données de la requête.
     * @return Une réponse HTTP indiquant le résultat de la création de l'article de boutique.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
        @RequestBody @Valid ShopItemForm shopItemForm,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }
        
        Optional<ShopCategoryEntity> optionalShopCategory = this.shopCategoryService.getOneById(shopItemForm.getShopCategory());
        Optional<ServerEntity> optionalServer = this.serverService.getOneById(shopItemForm.getServer());
        if(optionalShopCategory.isPresent() && optionalServer.isPresent()) {
            ShopItemEntity shopItemEntity = shopItemForm.toEntity();
            shopItemEntity.setShopCategory(optionalShopCategory.get());
            shopItemEntity.setServer(optionalServer.get());
            this.shopItemService.create(shopItemEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "L'item du shop a bien été créée."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de créer l'item du shop a jours, veuillez contacter un administrateur."));
        }
    }

    /**
     * Récupère la liste de tous les articles de boutique.
     *
     * @return Une liste d'objets ShopItemDTO représentant les articles de boutique.
     */
    @GetMapping(path = {"/list"})
    public List<ShopItemDTO> findAllAction() {
        return this.shopItemService.getAll().stream().map(ShopItemDTO::toDTO).toList();
    }

    /**
     * Récupère un article de boutique par son identifiant.
     *
     * @param id L'identifiant de l'article de boutique à récupérer.
     * @return Une réponse HTTP contenant l'article de boutique ou un message d'erreur.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<ShopItemEntity> newsEntity = this.shopItemService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(ShopItemDTO.toDTO(newsEntity.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'item du shop avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime un article de boutique par son identifiant.
     *
     * @param id L'identifiant de l'article de boutique à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression de l'article de boutique.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<ShopItemEntity> shopItemEntity = this.shopItemService.getOneById(id);
            if(shopItemEntity.isPresent()) {
                this.shopItemService.remove(id);
            return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "L'item du shop avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "L'item du shop avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}