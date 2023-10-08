package be.bnair.bevo.controllers;

import be.bnair.bevo.models.dto.UserDTO;
import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.RegisterForm;
import be.bnair.bevo.models.forms.UserForm;
import be.bnair.bevo.models.forms.UserUpdateEmailForm;
import be.bnair.bevo.models.forms.UserUpdatePasswordForm;
import be.bnair.bevo.services.RankService;
import be.bnair.bevo.services.ShopTransactionService;
import be.bnair.bevo.services.UserService;

import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contrôleur pour la gestion des utilisateurs.
 * Ce contrôleur permet de gérer les opérations liées aux utilisateurs, telles que la mise à jour
 * des informations de l'utilisateur, la mise à jour du mot de passe, la récupération de la liste
 * des utilisateurs, la récupération d'un utilisateur par ID, et la suppression d'un utilisateur.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/users"})
public class UserController {
    private final UserService userService;
    private final RankService rankService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ShopTransactionService shopTransactionService;

    /**
     * Constructeur du contrôleur des utilisateurs.
     *
     * @param jwtUtil               Le service JWT pour l'authentification des utilisateurs.
     * @param userService           Le service de gestion des utilisateurs.
     * @param passwordEncoder       Le service pour encoder les mots de passe.
     * @param shopTransactionService Le service de gestion des transactions de boutique.
     * @param rankService           Le service de gestion des niveaux d'utilisateur.
     */
    public UserController(JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder, ShopTransactionService shopTransactionService, RankService rankService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.shopTransactionService = shopTransactionService;
        this.rankService = rankService;
    }

    /**
     * Met à jour les informations de l'utilisateur.
     *
     * @param request       La requête HTTP.
     * @param id            L'identifiant de l'utilisateur à mettre à jour.
     * @param userForm      Les données de l'utilisateur à mettre à jour.
     * @param bindingResult Le résultat de la validation des données de la requête.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour de l'utilisateur.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody @Valid UserForm userForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<UserEntity> optionUserEntity = this.userService.getOneById(id);
        Optional<RankEntity> optionalRankEntity = this.rankService.getOneById(userForm.getRank());
        if(optionUserEntity.isPresent()) {
            UserEntity userEntity = userForm.toEntity();
            if(optionalRankEntity.isPresent()) userEntity.setRank(optionalRankEntity.get());
            System.out.println(userForm.toString());
            if(userForm.getPassword() != null && userForm.getPassword().length() > 0) userEntity.setPassword(passwordEncoder.encode(userForm.getPassword()));
            userEntity.setId(id);
            try {
                this.userService.update(id, userEntity);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "L'utilisateur a bien été mise a jour."));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'utilisateur a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'utilisateur a jours, veuillez contacter un administrateur."));
    }

    /**
     * Met à jour l'adresse e-mail de l'utilisateur.
     *
     * @param request              La requête HTTP.
     * @param userUpdateEmailForm  Les données de mise à jour de l'adresse e-mail de l'utilisateur.
     * @param bindingResult        Le résultat de la validation des données de la requête.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour de l'adresse e-mail de l'utilisateur.
     */
    @PatchMapping(path = {"/update/email"})
    public ResponseEntity<Object> updateEmailAction(
            HttpServletRequest request,
            @RequestBody @Valid UserUpdateEmailForm userUpdateEmailForm,
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

        if(userUpdateEmailForm.getEmail().equals(userDetails.getEmail())) {
            userDetails.setEmail(userUpdateEmailForm.getNew_email());
            try {
                this.userService.update(userDetails.getId(), userDetails);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "L'utilisateur a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'utilisateur a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'email actuel spécifié n'est pas bon."));
    }

    /**
     * Met à jour le mot de passe de l'utilisateur.
     *
     * @param request                  La requête HTTP.
     * @param userUpdatePasswordForm   Les données de mise à jour du mot de passe de l'utilisateur.
     * @param bindingResult            Le résultat de la validation des données de la requête.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour du mot de passe de l'utilisateur.
     */
    @PatchMapping(path = {"/update/password"})
    public ResponseEntity<Object> updatePasswordAction(
            HttpServletRequest request,
            @RequestBody @Valid UserUpdatePasswordForm userUpdatePasswordForm,
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

        if (passwordEncoder.matches(userUpdatePasswordForm.getPassword(), userDetails.getPassword())) {
            userDetails.setPassword(passwordEncoder.encode(userUpdatePasswordForm.getNew_password()));
            try {
                this.userService.update(userDetails.getId(), userDetails);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "L'utilisateur a bien été mise a jour."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre l'utilisateur a jours, veuillez contacter un administrateur."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le mot de passe actuel spécifié n'est pas bon."));
    }

    /**
     * Récupère la liste de tous les utilisateurs avec pagination.
     *
     * @param page Le numéro de la page de résultats (par défaut : 0).
     * @param size Le nombre d'utilisateurs par page (par défaut : 20).
     * @return Une réponse HTTP contenant la liste paginée de tous les utilisateurs.
     */
    @GetMapping("/list")
    public ResponseEntity<Object> findAllAction(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "20", required = false) int size) {
        List<UserEntity> allUsers = userService.getAll();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allUsers.size());

        try {
            return ResponseEntity.ok(allUsers
                    .subList(fromIndex, toIndex)
                    .stream()
                    .map(UserDTO::toDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    /**
     * Récupère un utilisateur par ID.
     *
     * @param request La requête HTTP.
     * @param id      L'identifiant de l'utilisateur à récupérer.
     * @return Une réponse HTTP contenant les informations de l'utilisateur.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(HttpServletRequest request, @PathVariable Long id) {
        UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

        Optional<UserEntity> userEntity = this.userService.getOneById(id);
        if(userEntity.isPresent()) {
            if(userDetails.getId() == id)
                return ResponseEntity.ok().body(UserDTO.toDTO(userEntity.get()));
            else {
                if(userDetails.getRank().getId() == 1L)
                    return ResponseEntity.ok().body(UserDTO.toDTO(userEntity.get()));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Vous ne pouvez pas récupèrer un utilisateur autre que vous !"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "L'utilisateur avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime un utilisateur par ID.
     *
     * @param request La requête HTTP.
     * @param id      L'identifiant de l'utilisateur à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression de l'utilisateur.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(HttpServletRequest request, @PathVariable Long id) {
        try {
            Optional<UserEntity> optionalUserEntity = this.userService.getOneById(id);
            if(optionalUserEntity.isPresent()) {
                UserEntity userEntity = optionalUserEntity.get();
                userEntity.setEnabled(false);
                userService.update(id, userEntity);
                return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "L'utilisateur avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "L'utilisateur avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}