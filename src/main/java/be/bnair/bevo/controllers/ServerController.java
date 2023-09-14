package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.forms.ServerForm;
import be.bnair.bevo.services.ServerService;

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

@RestController
@RequestMapping(path = {"/servers"})
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            @PathVariable Long id,
            @RequestBody @Valid ServerForm serverForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<ServerEntity> optionalNewsEntity = this.serverService.getOneById(id);
        if(optionalNewsEntity.isPresent()) {
            ServerEntity serverEntity = serverForm.toEntity();
            serverEntity.setId(id);
            try {
                this.serverService.update(id, serverEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "Le serveur a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre le serveur a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre le serveur a jours, veuillez contacter un administrateur."));
    }

    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid ServerForm serverForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        ServerEntity serverEntity = serverForm.toEntity();
        this.serverService.create(serverEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "Le serveur a bien été créée."));
    }

    @GetMapping(path = {"/list"})
    public List<ServerEntity> findAllAction() {
        return this.serverService.getAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<ServerEntity> newsEntity = this.serverService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(newsEntity.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le serveur avec l'id " + id + " n'existe pas."));
    }

    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<ServerEntity> paypalOfferEntity = this.serverService.getOneById(id);
            if(paypalOfferEntity.isPresent()) {
                this.serverService.remove(id);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Le serveur avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Le serveur avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}