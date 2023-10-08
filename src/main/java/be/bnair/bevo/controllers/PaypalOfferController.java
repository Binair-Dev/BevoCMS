package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.models.forms.PaypalOfferForm;
import be.bnair.bevo.services.PaypalOfferService;
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
 * Contrôleur pour la gestion des offres PayPal.
 * Ce contrôleur permet de gérer les offres PayPal, y compris la création, la mise à jour,
 * la suppression et la récupération des offres PayPal.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/paypal-offers"})
public class PaypalOfferController {
    private final PaypalOfferService paypalOfferService;

    /**
     * Constructeur de la classe PaypalOfferController.
     *
     * @param paypalOfferService Le service gérant les offres PayPal.
     */
    public PaypalOfferController(PaypalOfferService paypalOfferService) {
        this.paypalOfferService = paypalOfferService;
    }

    /**
     * Met à jour une offre PayPal existante avec les données fournies.
     *
     * @param id              L'ID de l'offre PayPal à mettre à jour.
     * @param paypalOfferForm Les données de l'offre PayPal à mettre à jour.
     * @param bindingResult   Résultat de la validation des données.
     * @return                Réponse HTTP indiquant le statut de la mise à jour.
     */
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

        Optional<PaypalOfferEntity> optionalNewsEntity = this.paypalOfferService.getOneById(id);
        if(optionalNewsEntity.isPresent()) {
            PaypalOfferEntity paypalOfferEntity = optionalNewsEntity.get();
            paypalOfferEntity.setTitle(paypalOfferForm.getTitle());
            paypalOfferEntity.setDescription(paypalOfferForm.getDescription());
            paypalOfferEntity.setPrice(paypalOfferForm.getPrice());
            paypalOfferEntity.setCredit(paypalOfferForm.getCredit());
            try {
                this.paypalOfferService.update(id, paypalOfferEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "L'offre paypal a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'offre paypal a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'offre paypal a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée une nouvelle offre PayPal avec les données fournies.
     *
     * @param paypalOfferForm Les données de la nouvelle offre PayPal.
     * @param bindingResult   Résultat de la validation des données.
     * @return                Réponse HTTP indiquant le statut de la création.
     */
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

        PaypalOfferEntity paypalOfferEntity = paypalOfferForm.toEntity();
        paypalOfferEntity.setTitle(paypalOfferEntity.getTitle());
        paypalOfferEntity.setDescription(paypalOfferEntity.getDescription());
        paypalOfferEntity.setPrice(paypalOfferEntity.getPrice());
        paypalOfferEntity.setCredit(paypalOfferEntity.getCredit());
        this.paypalOfferService.create(paypalOfferEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "L'offre paypal a bien été créée."));
    }

    /**
     * Récupère toutes les offres PayPal.
     *
     * @return Liste des offres PayPal sous forme d'entités.
     */
    @GetMapping(path = {"/list"})
    public List<PaypalOfferEntity> findAllAction() {
        return this.paypalOfferService.getAll();
    }

    /**
     * Récupère une offre PayPal par son ID.
     *
     * @param id  L'ID de l'offre PayPal à récupérer.
     * @return    Réponse HTTP contenant l'offre PayPal sous forme d'entité.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<PaypalOfferEntity> newsEntity = this.paypalOfferService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(newsEntity.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'offre paypal avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime une offre PayPal par son ID.
     *
     * @param id  L'ID de l'offre PayPal à supprimer.
     * @return    Réponse HTTP indiquant le statut de la suppression.
     */
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