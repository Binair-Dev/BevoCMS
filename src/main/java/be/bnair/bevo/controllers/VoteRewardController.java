package be.bnair.bevo.controllers;

import be.bnair.bevo.models.dto.VoteRewardDTO;
import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.models.forms.VoteRewardForm;
import be.bnair.bevo.services.ServerService;
import be.bnair.bevo.services.VoteRewardService;

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
 * Contrôleur pour la gestion des récompenses de vote.
 * Ce contrôleur permet de gérer les opérations CRUD (Create, Read, Update, Delete)
 * sur les récompenses de vote.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/vote-rewards"})
public class VoteRewardController {
    private final VoteRewardService voteRewardService;
    private final ServerService serverService;

    /**
     * Constructeur de la classe VoteRewardController.
     *
     * @param voteRewardService Le service de gestion des récompenses de vote.
     * @param serverService     Le service de gestion des serveurs.
     */
    public VoteRewardController(VoteRewardService voteRewardService, ServerService serverService) {
        this.voteRewardService = voteRewardService;
        this.serverService = serverService;
    }

    /**
     * Met à jour une récompense de vote existante.
     *
     * @param id              L'identifiant de la récompense de vote à mettre à jour.
     * @param voteRewardForm  Le formulaire contenant les données de la récompense de vote à mettre à jour.
     * @param bindingResult   Le résultat de la validation des données.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour de la récompense de vote.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            @PathVariable Long id,
            @RequestBody @Valid VoteRewardForm voteRewardForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<VoteRewardEntity> optionalVoteRewardEntity = this.voteRewardService.getOneById(id);
        Optional<ServerEntity> optionalServerEntity = this.serverService.getOneById(voteRewardForm.getServer());
        if(optionalVoteRewardEntity.isPresent() && optionalServerEntity.isPresent()) {
            VoteRewardEntity voteRewardEntity = voteRewardForm.toEntity();
            voteRewardEntity.setServer(optionalServerEntity.get());
            try {
                this.voteRewardService.update(id, voteRewardEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "La récompense de vote a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la récompense de vote a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la récompense de vote a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée une nouvelle récompense de vote.
     *
     * @param voteRewardForm  Le formulaire contenant les données de la nouvelle récompense de vote.
     * @param bindingResult   Le résultat de la validation des données.
     * @return Une réponse HTTP indiquant le résultat de la création de la récompense de vote.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid VoteRewardForm voteRewardForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }
        Optional<ServerEntity> optionalServerEntity = this.serverService.getOneById(voteRewardForm.getServer());
        if(optionalServerEntity.isPresent()) {
            VoteRewardEntity voteRewardEntity = voteRewardForm.toEntity();
            voteRewardEntity.setServer(optionalServerEntity.get());
            this.voteRewardService.create(voteRewardEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La récompense de vote a bien été créée."));
        } 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de créer la récompense de vote."));
    }

    /**
     * Récupère la liste de toutes les récompenses de vote.
     *
     * @return Une liste de récompenses de vote sous forme de DTO.
     */
    @GetMapping(path = {"/list"})
    public List<VoteRewardDTO> findAllAction() {
        return this.voteRewardService.getAll().stream().map(VoteRewardDTO::toDTO).toList();
    }


    /**
     * Récupère une récompense de vote par son identifiant.
     *
     * @param id L'identifiant de la récompense de vote à récupérer.
     * @return Une réponse HTTP contenant la récompense de vote recherchée ou un message d'erreur.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<VoteRewardEntity> newsEntity = this.voteRewardService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(VoteRewardDTO.toDTO(newsEntity.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "La récompense de vote avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime une récompense de vote existante par son identifiant.
     *
     * @param id L'identifiant de la récompense de vote à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression de la récompense de vote.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<VoteRewardEntity> paypalOfferEntity = this.voteRewardService.getOneById(id);
            if(paypalOfferEntity.isPresent()) {
                this.voteRewardService.remove(id);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "La récompense de vote avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "La récompense de vote avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}