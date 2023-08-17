package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.forms.ShopCategoryForm;
import be.bnair.bevo.services.ShopCategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
import be.bnair.bevo.utils.AuthUtils;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/shop-categories"})
public class ShopCategoryController {
    private final ShopCategoryService shopCategoryService;

    public ShopCategoryController(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }

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

        UserDetails userDetails = AuthUtils.getUserDetailsFromToken();
        Optional<ShopCategoryEntity> optionalShopCategory = this.shopCategoryService.getOneById(id);
        if(optionalShopCategory.isPresent()) {
            if(userDetails != null) {
                ShopCategoryEntity serverEntity = shopCategoryForm.toEntity();
                serverEntity.setShopItems(optionalShopCategory.get().getShopItems());
                serverEntity.setId(id);
                try {
                    this.shopCategoryService.update(id, serverEntity);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La catégorie shop a bien été mise a jour."));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre la catégorie du shop a jours, veuillez contacter un administrateur."));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
    }

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

        UserDetails userDetails = AuthUtils.getUserDetailsFromToken();
        if(userDetails != null) {
            ShopCategoryEntity serverEntity = shopCategoryForm.toEntity();
            this.shopCategoryService.create(serverEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La catégorie shop a bien été créée."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
    }

    @GetMapping(path = {"/list"})
    public List<ShopCategoryEntity> findAllAction() {
        return this.shopCategoryService.getAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<ShopCategoryEntity> newsEntity = this.shopCategoryService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(newsEntity.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "La catégorie shop avec l'id " + id + " n'existe pas."));
    }

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