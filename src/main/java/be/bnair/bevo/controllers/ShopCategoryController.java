package be.bnair.bevo.controllers;

import be.bnair.bevo.models.dto.ShopCategoryDTO;
import be.bnair.bevo.models.dto.ShopItemDTO;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.forms.ShopCategoryForm;
import be.bnair.bevo.services.ShopCategoryService;

import be.bnair.bevo.services.ShopItemService;
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

import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des catégories de boutique (shop categories).
 * Ce contrôleur permet de gérer les catégories de boutique, y compris la création, la mise à jour, la suppression et la récupération des informations sur les catégories de boutique.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/shop-categories"})
public class ShopCategoryController {
    private final ShopCategoryService shopCategoryService;
    private final ShopItemService shopItemService;

    /**
     * Constructeur du contrôleur de catégories de boutique.
     *
     * @param shopCategoryService Le service de gestion des catégories de boutique.
     * @param shopItemService     Le service de gestion des articles de boutique.
     */
    public ShopCategoryController(ShopCategoryService shopCategoryService, ShopItemService shopItemService) {
        this.shopCategoryService = shopCategoryService;
        this.shopItemService = shopItemService;
    }

    /**
     * Met à jour les informations d'une catégorie de boutique.
     *
     * @param id               L'identifiant de la catégorie de boutique à mettre à jour.
     * @param shopCategoryForm Les données du formulaire de mise à jour de la catégorie de boutique.
     * @param bindingResult    Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour de la catégorie de boutique.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            @PathVariable Long id,
            @RequestBody @Valid ShopCategoryForm shopCategoryForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<ShopCategoryEntity> optionalShopCategory = this.shopCategoryService.getOneById(id);
        if(optionalShopCategory.isPresent()) {
            ShopCategoryEntity serverEntity = shopCategoryForm.toEntity();
            serverEntity.setId(id);
            try {
                this.shopCategoryService.update(id, serverEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "La catégorie shop a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la catégorie du shop a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la catégorie du shop a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée une nouvelle catégorie de boutique.
     *
     * @param shopCategoryForm Les données du formulaire de création de la catégorie de boutique.
     * @param bindingResult    Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de la création de la catégorie de boutique.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid ShopCategoryForm shopCategoryForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        ShopCategoryEntity serverEntity = shopCategoryForm.toEntity();
        this.shopCategoryService.create(serverEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La catégorie shop a bien été créée."));
    }

    /**
     * Récupère la liste de toutes les catégories de boutique avec leurs articles associés.
     *
     * @return Une liste de DTO de catégories de boutique, chaque DTO contenant également une liste de DTO d'articles de boutique associés.
     */
    @GetMapping(path = {"/list"})
    public List<ShopCategoryDTO> findAllAction() {
        return this.shopCategoryService.getAll().stream()
                .map(cat -> ShopCategoryDTO.toDTO(cat, this.shopItemService.getAll().stream()
                        .filter(si -> si.getShopCategory().getId() == cat.getId())
                        .map(ShopItemDTO::toDTO)
                        .toList()))
                .toList();
    }

    /**
     * Récupère les informations d'une catégorie de boutique par son identifiant avec ses articles associés.
     *
     * @param id L'identifiant de la catégorie de boutique à récupérer.
     * @return Une réponse HTTP contenant les informations de la catégorie de boutique avec ses articles associés ou un message d'erreur si la catégorie de boutique n'existe pas.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<ShopCategoryEntity> newsEntity = this.shopCategoryService.getOneById(id);
        if(newsEntity.isPresent()) {
            ShopCategoryEntity entity = newsEntity.get();
            return ResponseEntity.ok().body(ShopCategoryDTO.toDTO(entity,
                            this.shopItemService.getAll().stream()
                                    .filter(si -> si.getShopCategory().getId() == entity.getId())
                                    .map(ShopItemDTO::toDTO)
                                    .toList()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "La catégorie shop avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime une catégorie de boutique par son identifiant.
     *
     * @param id L'identifiant de la catégorie de boutique à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression de la catégorie de boutique.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<ShopCategoryEntity> optional = this.shopCategoryService.getOneById(id);
            if(optional.isPresent()) {
                this.shopCategoryService.remove(id);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "La catégorie shop avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "La catégorie shop avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}