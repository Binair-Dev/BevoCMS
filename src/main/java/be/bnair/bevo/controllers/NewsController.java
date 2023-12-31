package be.bnair.bevo.controllers;

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

import be.bnair.bevo.models.dto.NewsDTO;
import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.NewsForm;
import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.NewsService;
import be.bnair.bevo.services.UserService;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contrôleur pour la gestion des actualités (news).
 * Ce contrôleur permet de gérer les actualités, y compris la création, la mise à jour, la suppression et la récupération des actualités.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/news"})
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du contrôleur NewsController.
     *
     * @param jwtUtil     Utilitaire JWT pour la gestion des tokens.
     * @param newsService Service d'actualités pour la gestion des actualités.
     * @param userService Service utilisateur pour la gestion des utilisateurs.
     */
    public NewsController(JwtUtil jwtUtil, NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Met à jour une actualité existante avec les données fournies.
     *
     * @param request        Requête HTTP.
     * @param id             L'ID de l'actualité à mettre à jour.
     * @param newsForm       Les données de l'actualité à mettre à jour.
     * @param bindingResult  Résultat de la validation des données.
     * @return               Réponse HTTP indiquant le statut de la mise à jour.
     */
    @PatchMapping(path = {"/update/{id}"})
    public ResponseEntity<Object> patchAction(
            HttpServletRequest request,
        @PathVariable Long id,
        @RequestBody @Valid NewsForm newsForm,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7);
        String username = this.jwtUtil.getUsernameFromToken(jwtToken);
        Optional<UserEntity> entityFromToken = this.userService.getOneByUsername(username);
        if(entityFromToken.isPresent()) {
            UserDetails userDetails = entityFromToken.get();
            Optional<NewsEntity> optionalNewsEntity = this.newsService.getOneById(id);
            if(optionalNewsEntity.isPresent()) {
                if(userDetails != null) {
                    NewsEntity newsEntity = optionalNewsEntity.get();
                    newsEntity.setTitle(newsForm.getTitle());
                    newsEntity.setDescription(newsForm.getDescription());
                    newsEntity.setImage(newsForm.getImage());
                    try {
                        this.newsService.update(id, newsEntity);
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse(HttpStatus.ACCEPTED.value(), "La news a bien été mise a jour."));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Impossible de mettre la News a jours, veuillez contacter un administrateur."));
                    }
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver l'utilisateur."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de mettre la News a jours, veuillez contacter un administrateur."));
    }

    /**
     * Crée une nouvelle actualité avec les données fournies.
     *
     * @param request        Requête HTTP.
     * @param newsForm       Les données de la nouvelle actualité.
     * @param bindingResult  Résultat de la validation des données.
     * @return               Réponse HTTP indiquant le statut de la création.
     */
    @PostMapping(path = {"/create"})
    public ResponseEntity<Object> createAction(
            HttpServletRequest request,
        @RequestBody @Valid NewsForm newsForm,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7);
        String username = this.jwtUtil.getUsernameFromToken(jwtToken);
        Optional<UserEntity> entityFromToken = this.userService.getOneByUsername(username);
        if(entityFromToken.isPresent()) {
            UserDetails userDetails = entityFromToken.get();
            if(userDetails != null) {
                Optional<UserEntity> userEntity = this.userService.getOneByUsername(userDetails.getUsername());
                if(userEntity.isPresent()) {
                    NewsEntity newsEntity = newsForm.toEntity();
                    newsEntity.setDate(LocalDate.now());
                    newsEntity.setAuthor(userEntity.get());
                    newsEntity.setPublished(false);
                    this.newsService.create(newsEntity);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED.value(), "La news a bien été créée."));
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Impossible de trouver l'utilisateur."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver l'utilisateur."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver l'utilisateur."));
    }

    /**
     * Récupère toutes les actualités.
     *
     * @return Liste des actualités sous forme de DTO.
     */
    @GetMapping(path = {"/list"})
    public List<NewsDTO> findAllAction() {
        return this.newsService.getAll().stream().map(e -> NewsDTO.toDTO(e)).toList();
    }

    /**
     * Récupère les dernières actualités jusqu'à la limite spécifiée.
     *
     * @param limit  Limite du nombre d'actualités à récupérer.
     * @return       Liste des dernières actualités sous forme de DTO.
     */
    @GetMapping(path = {"/list/{limit}"})
    public List<NewsDTO> findLastThreeNews(@PathVariable int limit) {
        List<NewsDTO> list = this.newsService.getAll()
                .stream()
                .sorted(Comparator.comparing(NewsEntity::getId).reversed())
                .limit(limit)
                .map(e -> NewsDTO.toDTO(e))
                .collect(Collectors.toList());

        return list;
    }

    /**
     * Récupère une actualité par son ID.
     *
     * @param id  L'ID de l'actualité à récupérer.
     * @return    Réponse HTTP contenant l'actualité sous forme de DTO.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findByIdAction(@PathVariable Long id) {
        Optional<NewsEntity> newsEntity = this.newsService.getOneById(id);
        if(newsEntity.isPresent()) {
            return ResponseEntity.ok().body(NewsDTO.toDTO(newsEntity.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "La news avec l'id " + id + " n'existe pas."));
    }

    /**
     * Supprime une actualité par son ID.
     *
     * @param id  L'ID de l'actualité à supprimer.
     * @return    Réponse HTTP indiquant le statut de la suppression.
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Object> deleteByIdAction(@PathVariable Long id) {
        try {
            Optional<NewsEntity> newsEntity = this.newsService.getOneById(id);
            if(newsEntity.isPresent()) {
                this.newsService.remove(id);
            return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "La news avec l'id " + id + " a bien été supprimée."));
            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "La news avec l'id " + id + "' n'existe pas."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Une erreur est survenue, veuillez contacter un administrateur. (Erreur: " + e.getMessage() + ")"));
        }
    }
}