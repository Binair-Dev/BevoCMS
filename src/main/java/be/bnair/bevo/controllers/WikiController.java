package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.security.UserEntity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bnair.bevo.models.entities.WikiEntity;
import be.bnair.bevo.models.forms.WikiForm;
import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.WikiService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des wikis.
 * Ce contrôleur permet de gérer les opérations CRUD (Create, Read, Update, Delete)
 * sur les wikis.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@RestController
@RequestMapping(path = {"/wikis"})
public class WikiController {
    private final WikiService wikiService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur de la classe WikiController.
     *
     * @param wikiService Le service de gestion des wikis.
     * @param userService Le service de gestion des utilisateurs.
     * @param jwtUtil     Le service JWT pour l'authentification des utilisateurs.
     */
    public WikiController(WikiService wikiService, UserService userService, JwtUtil jwtUtil) {
        this.wikiService = wikiService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Met à jour un wiki existant.
     *
     * @param request     La requête HTTP.
     * @param id          L'identifiant du wiki à mettre à jour.
     * @param wikiForm    Le formulaire contenant les données du wiki à mettre à jour.
     * @param bindingResult Le résultat de la validation des données.
     * @return Une réponse HTTP indiquant le résultat de la mise à jour du wiki.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            HttpServletRequest request,
        @PathVariable Long id,
        @RequestBody @Valid WikiForm wikiForm,
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

        Optional<WikiEntity> optionalWikiEntity = this.wikiService.getOneById(id);
        if(optionalWikiEntity.isPresent()) {
            if(userDetails != null) {
                WikiEntity newsEntity = optionalWikiEntity.get();
                newsEntity.setTitle(wikiForm.getTitle());
                newsEntity.setDescription(wikiForm.getDescription());
                newsEntity.setImage(wikiForm.getImage());
                try {
                    this.wikiService.update(id, newsEntity);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "Le Wiki a bien été mise a jour."));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre le Wiki a jours, veuillez contacter un administrateur."));
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre le Wiki a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée un nouveau wiki.
     *
     * @param request     La requête HTTP.
     * @param wikiForm    Le formulaire contenant les données du nouveau wiki.
     * @param bindingResult Le résultat de la validation des données.
     * @return Une réponse HTTP indiquant le résultat de la création du wiki.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            HttpServletRequest request,
        @RequestBody @Valid WikiForm wikiForm,
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
        else {
            WikiEntity newsEntity = wikiForm.toEntity();
            this.wikiService.create(newsEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "Le Wiki a bien été créée."));
        }
    }

    /**
     * Récupère la liste de tous les wikis.
     *
     * @return Une liste de wikis.
     */
    @GetMapping(path = {"/list"})
    public List<WikiEntity> findAllAction() {
        return this.wikiService.getAll();
    }

    /**
     * Récupère un wiki par son identifiant.
     *
     * @param id L'identifiant du wiki à récupérer.
     * @return Une réponse HTTP contenant le wiki recherché ou un message d'erreur.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<WikiEntity> wOptional = this.wikiService.getOneById(id);
        if(wOptional.isPresent()) {
            return ResponseEntity.ok().body(wOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le Wiki avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime un wiki existant par son identifiant.
     *
     * @param id L'identifiant du wiki à supprimer.
     * @return Une réponse HTTP indiquant le résultat de la suppression du wiki.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<WikiEntity> wOptional = this.wikiService.getOneById(id);
            if(wOptional.isPresent()) {
                this.wikiService.remove(id);
            return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Le Wiki avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Le Wiki avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}