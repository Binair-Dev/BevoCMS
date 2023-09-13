package be.bnair.bevo.controllers;

import be.bnair.bevo.models.VoteTop;
import be.bnair.bevo.models.dto.VoteDTO;
import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.VoteForm;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.services.VoteService;

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
import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = {"/votes"})
public class VoteController {
    private final VoteService voteService;
    private final UserService userService;

    public VoteController(VoteService voteService, UserService userService) {
        this.voteService = voteService;
        this.userService = userService;
    }

    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            @PathVariable Long id,
            @RequestBody @Valid VoteForm voteForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<VoteEntity> optionalVoteEntity = this.voteService.getOneById(id);
        Optional<UserEntity> optionalUserEntity = this.userService.getOneById(voteForm.getUser());
        if(optionalVoteEntity.isPresent() && optionalUserEntity.isPresent()) {
            VoteEntity voteEntity = optionalVoteEntity.get();
            voteEntity.setUser(optionalUserEntity.get());
            try {
                this.voteService.update(id, voteEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "Le vote a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre le vote a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre le vote a jours, veuillez contacter un administrateur."));
    }

    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            @RequestBody @Valid VoteForm voteForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        VoteEntity voteEntity = voteForm.toEntity();
        
        this.voteService.create(voteEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "Le vote a bien été créée."));
    }

    @GetMapping(path = {"/list"})
    public List<VoteDTO> findAllAction() {
        return this.voteService.getAll().stream().map(VoteDTO::toDTO).toList();
    }

    @GetMapping(path = {"/list/top"})
    public List<VoteTop> findTopAction() {
        Map<Long, Integer> voteCounts = new HashMap<>();
        List<VoteEntity> allVotes = this.voteService.getAll();
        for (VoteEntity vote : allVotes) {
            Long userId = vote.getUser().getId();
            voteCounts.put(userId, voteCounts.getOrDefault(userId, 0) + 1);
        }

        List<VoteTop> topUserVotes = voteCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(10)
                .map(entry -> VoteTop.builder()
                        .user_name(this.userService.getOneById(entry.getKey()).isPresent() ? this.userService.getOneById(entry.getKey()).get().getNickname() : null)
                        .vote_amount(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        return topUserVotes;
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<VoteEntity> vOptional = this.voteService.getOneById(id);
        if(vOptional.isPresent()) {
            return ResponseEntity.ok().body(VoteDTO.toDTO(vOptional.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le vote avec l'id " + id + " n'existe pas."));
    }

    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<VoteEntity> eOptional = this.voteService.getOneById(id);
            if(eOptional.isPresent()) {
                this.voteService.remove(id);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Le vote avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Le vote avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}