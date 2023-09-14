package be.bnair.bevo.controllers;

import be.bnair.bevo.models.dto.TransactionDTO;
import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.ShopTransactionForm;
import be.bnair.bevo.models.forms.TransactionForm;
import be.bnair.bevo.services.ShopItemService;
import be.bnair.bevo.services.ShopTransactionService;
import be.bnair.bevo.services.TransactionService;
import be.bnair.bevo.services.UserService;

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

@RestController
@RequestMapping(path = {"/transactions"})
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService, ShopItemService shopItemService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid TransactionForm transactionForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }
        
        Optional<UserEntity> userEntityOptional = this.userService.getOneById(transactionForm.getUser());
        if(userEntityOptional.isPresent()) {
            TransactionEntity transactionEntity = transactionForm.toEntity();
            transactionEntity.setUser(userEntityOptional.get());
            this.transactionService.create(transactionEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La transaction a bien été créée."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de créer la transaction."));
    }

    @GetMapping(path = {"/list"})
    public List<TransactionDTO> findAllAction() {
        return this.transactionService.getAll().stream().map(TransactionDTO::toDTO).toList();
    }
}