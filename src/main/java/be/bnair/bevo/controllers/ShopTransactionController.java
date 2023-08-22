package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.ShopTransactionForm;
import be.bnair.bevo.services.ShopItemService;
import be.bnair.bevo.services.ShopTransactionService;
import be.bnair.bevo.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

@RestController
@RequestMapping(path = {"/shop-transactions"})
public class ShopTransactionController {
    private final ShopTransactionService shopTransactionService;
    private final UserService userService;
    private final ShopItemService shopItemService;

    public ShopTransactionController(ShopTransactionService shopTransactionService, UserService userService, ShopItemService shopItemService) {
        this.shopTransactionService = shopTransactionService;
        this.userService = userService;
        this.shopItemService = shopItemService;
    }

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
}