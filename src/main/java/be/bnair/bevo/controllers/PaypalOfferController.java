package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.models.forms.PaypalOfferForm;
import be.bnair.bevo.services.PaypalOfferService;
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

import be.bnair.bevo.models.dto.NewsDTO;
import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.NewsForm;
import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.NewsService;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.AuthUtils;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/paypal-offer"})
public class PaypalOfferController {
    private final PaypalOfferService paypalOfferService;

    public PaypalOfferController(PaypalOfferService paypalOfferService) {
        this.paypalOfferService = paypalOfferService;
    }

    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            @PathVariable Long id,
            @RequestBody @Valid PaypalOfferForm paypalOfferForm,
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
        Optional<PaypalOfferEntity> optionalNewsEntity = this.paypalOfferService.getOneById(id);
        if(optionalNewsEntity.isPresent()) {
            if(userDetails != null) {
                PaypalOfferEntity paypalOfferEntity = optionalNewsEntity.get();
                paypalOfferEntity.setTitle(paypalOfferForm.getTitle());
                paypalOfferEntity.setDescription(paypalOfferForm.getDescription());
                paypalOfferEntity.setPrice(paypalOfferForm.getPrice());
                paypalOfferEntity.setCredit(paypalOfferForm.getCredit());
                try {
                    this.paypalOfferService.update(id, paypalOfferEntity);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "L'offre paypal a bien été mise a jour."));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre l'offre paypal a jours, veuillez contacter un administrateur."));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
    }

    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid PaypalOfferForm paypalOfferForm,
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
            PaypalOfferEntity paypalOfferEntity = paypalOfferForm.toEntity();
            paypalOfferEntity.setTitle(paypalOfferEntity.getTitle());
            paypalOfferEntity.setDescription(paypalOfferEntity.getDescription());
            paypalOfferEntity.setPrice(paypalOfferEntity.getPrice());
            paypalOfferEntity.setCredit(paypalOfferEntity.getCredit());
            this.paypalOfferService.create(paypalOfferEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "L'offre paypal a bien été créée."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
    }

    @GetMapping(path = {"/list"})
    public List<PaypalOfferEntity> findAllAction() {
        return this.paypalOfferService.getAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<PaypalOfferEntity> newsEntity = this.paypalOfferService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(newsEntity.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'offre paypal avec l'id " + id + " n'existe pas."));
    }

    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<PaypalOfferEntity> paypalOfferEntity = this.paypalOfferService.getOneById(id);
            if(paypalOfferEntity.isPresent()) {
                this.paypalOfferService.remove(id);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "L'offre paypal avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "L'offre paypal avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}