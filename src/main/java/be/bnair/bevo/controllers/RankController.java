package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.forms.RankForm;
import be.bnair.bevo.repository.UserRepository;
import be.bnair.bevo.services.RankService;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.bridge.Message;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des rangs des utilisateurs.
 * Ce contrôleur permet de gérer les rangs, y compris la création, la mise à jour, la suppression et la récupération des rangs.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/ranks"})
public class RankController {
    private final RankService rankService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du contrôleur des rangs.
     *
     * @param rankService Le service de gestion des rangs.
     * @param userService Le service de gestion des utilisateurs.
     * @param jwtUtil     L'utilitaire JWT pour l'authentification.
     */
    public RankController(RankService rankService,
                          UserService userService, JwtUtil jwtUtil) {
        this.rankService = rankService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    /**
     * Met à jour un rang existant.
     *
     * @param request      La requête HTTP.
     * @param id           L'identifiant du rang à mettre à jour.
     * @param rankForm     Les données du formulaire de mise à jour du rang.
     * @param bindingResult Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody @Valid RankForm rankForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        UserDetails userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

        Optional<RankEntity> optionalRankEntity = this.rankService.getOneById(id);
        if(optionalRankEntity.isPresent() && optionalRankEntity.get().getId() != 1L) {
            if(userDetails != null) {
                RankEntity rankEntity = optionalRankEntity.get();
                rankEntity.setTitle(rankForm.getTitle());
                rankEntity.setPower(Long.valueOf(rankForm.getPower()));
                try {
                    this.rankService.update(id, rankEntity);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "Le Rank a bien été mise a jour."));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre le Rank a jours, veuillez contacter un administrateur."));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre le Rank a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée un nouveau rang.
     *
     * @param request      La requête HTTP.
     * @param rankForm     Les données du formulaire de création du rang.
     * @param bindingResult Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de la création du rang.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            HttpServletRequest request,
            @RequestBody @Valid RankForm rankForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        UserDetails userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

        if(userDetails != null) {
            RankEntity rankEntity = rankForm.toEntity();
            this.rankService.create(rankEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "Le Rank a bien été créée."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
    }

    /**
     * Récupère la liste de tous les rangs.
     *
     * @return La liste de tous les rangs.
     */
    @GetMapping(path = {"/list"})
    public List<RankEntity> findAllAction() {
        return this.rankService.getAll();
    }

    /**
     * Récupère un rang par son identifiant.
     *
     * @param id L'identifiant du rang à récupérer.
     * @return Une réponse HTTP contenant le rang récupéré.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<RankEntity> rankEntityOptional = this.rankService.getOneById(id);
        if(rankEntityOptional.isPresent()) {
            return ResponseEntity.ok().body(rankEntityOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le Rank avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime un rang par son identifiant.
     *
     * @param id L'identifiant du rang à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression du rang.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<RankEntity> rankEntityOptional = this.rankService.getOneById(id);
            if(rankEntityOptional.isPresent()) {
                if(rankEntityOptional.get().getId() != 1L && rankEntityOptional.get().getId() != 2L) {
                    this.rankService.remove(id);
                    return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Le Rank avec l'id " + id + " a bien été supprimée."));
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le Rank Administrateur ne peut pas être supprimé !"));
                }
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Le Rank avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}