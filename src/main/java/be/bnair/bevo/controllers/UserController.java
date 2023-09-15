package be.bnair.bevo.controllers;

import be.bnair.bevo.models.dto.UserDTO;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.RegisterForm;
import be.bnair.bevo.services.UserService;

import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(path = {"/users"})
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil, UserService userService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody @Valid RegisterForm registerForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

        Optional<UserEntity> optionUserEntity = this.userService.getOneById(id);
        if(optionUserEntity.isPresent()) {
            UserEntity userEntity = registerForm.toEntity();
            userEntity.setId(id);
            try {
                if(userDetails.getRank().getId() == 1L)
                    this.userService.update(id, userEntity, true);
                this.userService.update(id, userEntity, false);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "L'utilisateur a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre le serveur a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre le serveur a jours, veuillez contacter un administrateur."));
    }

    @GetMapping(path = {"/list"})
    public List<UserDTO> findAllAction() {
        return this.userService.getAll().stream().map(UserDTO::toDTO).toList();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(HttpServletRequest request, @PathVariable Long id) {
        UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

        Optional<UserEntity> userEntity = this.userService.getOneById(id);
        if(userEntity.isPresent()) {
            if(userDetails.getUsername().equals(userEntity.get().getUsername()))
                return ResponseEntity.ok().body(UserDTO.toDTO(userEntity.get()));
            else {
                if(userDetails.getRank().getId() == 1L)
                    return ResponseEntity.ok().body(UserDTO.toDTO(userEntity.get()));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Vous ne pouvez pas récupèrer un utilisateur autre que vous !"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'utilisateur avec l'id " + id + " n'existe pas."));
    }

    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(HttpServletRequest request, @PathVariable Long id) {
        try {
            UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
            if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

            Optional<UserEntity> optionalUserEntity = this.userService.getOneById(id);
            if(optionalUserEntity.isPresent()) {
                UserEntity userEntity = optionalUserEntity.get();
                userEntity.setEnabled(false);
                if(userDetails.getRank().getId() == 1L)
                    userService.update(id, userEntity, true);
                userService.update(id, userEntity, false);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "L'utilisateur avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "L'utilisateur avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}