package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.models.forms.RuleForm;
import be.bnair.bevo.services.RuleService;
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
 * Contrôleur pour la gestion des règles.
 * Ce contrôleur permet de gérer les règles, y compris la création, la mise à jour, la suppression et la récupération des règles.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@RestController
@RequestMapping(path = {"/rules"})
public class RuleController {
    private final RuleService ruleService;

    /**
     * Constructeur du contrôleur des règles.
     *
     * @param ruleService Le service de gestion des règles.
     */
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     * Met à jour une règle existante.
     *
     * @param id           L'identifiant de la règle à mettre à jour.
     * @param ruleForm     Les données du formulaire de mise à jour de la règle.
     * @param bindingResult Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            @PathVariable Long id,
            @RequestBody @Valid RuleForm ruleForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<RuleEntity> optionalRuleEntity = this.ruleService.getOneById(id);
        if(optionalRuleEntity.isPresent()) {
            RuleEntity ruleEntity = optionalRuleEntity.get();
            ruleEntity.setTitle(ruleForm.getTitle());
            ruleEntity.setDescription(ruleForm.getDescription());
            try {
                this.ruleService.update(id, ruleEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "La règle a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la règle a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la règle a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée une nouvelle règle.
     *
     * @param ruleForm     Les données du formulaire de création de la règle.
     * @param bindingResult Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de la création de la règle.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid RuleForm ruleForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        RuleEntity ruleEntity = ruleForm.toEntity();
        ruleEntity.setTitle(ruleForm.getTitle());
        ruleEntity.setDescription(ruleForm.getDescription());
        this.ruleService.create(ruleEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La règle a bien été créée."));
    }

    /**
     * Récupère la liste de toutes les règles.
     *
     * @return La liste de toutes les règles.
     */
    @GetMapping(path = {"/list"})
    public List<RuleEntity> findAllAction() {
        return this.ruleService.getAll();
    }

    /**
     * Récupère une règle par son identifiant.
     *
     * @param id L'identifiant de la règle à récupérer.
     * @return Une réponse HTTP contenant la règle récupérée.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<RuleEntity> newsEntity = this.ruleService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(newsEntity.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "La règle avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime une règle par son identifiant.
     *
     * @param id L'identifiant de la règle à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression de la règle.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<RuleEntity> paypalOfferEntity = this.ruleService.getOneById(id);
            if(paypalOfferEntity.isPresent()) {
                this.ruleService.remove(id);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "La règle avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "La règle avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}