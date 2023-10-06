package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.models.forms.RegisterForm;
import be.bnair.bevo.services.RankService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import be.bnair.bevo.models.responses.AuthResponse;
import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.models.forms.LoginForm;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.JwtUtil;

/**
 * Contrôleur pour la gestion de la sécurité (authentification et autorisation).
 * Ce contrôleur permet de gérer l'authentification et l'autorisation des utilisateurs, y compris la connexion, l'enregistrement et la vérification du token.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final JwtUtil utils;
    private final PasswordEncoder passwordEncoder;
    private final UserService securityService;
    private final RankService rankService;

    /**
     * Constructeur du contrôleur de sécurité.
     *
     * @param utils           L'utilitaire JWT pour la gestion des tokens.
     * @param passwordEncoder L'encodeur de mots de passe.
     * @param securityService Le service de gestion de la sécurité.
     * @param rankService     Le service de gestion des rangs.
     */
    public SecurityController(JwtUtil utils, PasswordEncoder passwordEncoder, UserService securityService, RankService rankService) {
        this.utils = utils;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.rankService = rankService;
    }

    /**
     * Authentifie un utilisateur en vérifiant les informations de connexion.
     *
     * @param request        La requête HTTP.
     * @param form           Les données du formulaire de connexion.
     * @param bindingResult  Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de l'authentification.
     */
    @PostMapping(path = {"/login"})
    public ResponseEntity<Object> loginAction(
            HttpServletRequest request,
            @RequestBody @Valid LoginForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        } else {
            try {
                UserDetails user = this.securityService.loadUserByUsername(form.getUsername());
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Utilisateur introuvable."));
                } else {
                    if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
                        return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Le mot de passe ne correspond pas."));
                    }
                }
            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "L'utilisateur n'existe pas."));
            }
        }
    }

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param form          Les données du formulaire d'enregistrement.
     * @param bindingResult Les résultats de la validation du formulaire.
     * @return Une réponse HTTP indiquant le résultat de l'enregistrement de l'utilisateur.
     */
    @PostMapping(path = {"/register"})
    public ResponseEntity<Object> registerAction(
            @RequestBody @Valid RegisterForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for(int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                errorList.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList));
        }

        Optional<RankEntity> defaultRank = rankService.getOneById(2L);
        Optional<UserEntity> exist = this.securityService.getOneByUsername(form.getUsername());
        if(exist.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le nom d'utilisateur est déjà utilisé."));
        }

        if(defaultRank.isPresent()) {
            UserEntity entity = form.toEntity();
            entity.setRank(defaultRank.get());
            UserDetails user = this.securityService.create(entity);
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer cet utilisateur, rank par défaut introuvable."));
    }

    /**
     * Vérifie la validité du token d'authentification.
     *
     * @return Une réponse HTTP indiquant la validité du token.
     */
    @GetMapping(path = "/authenticated")
    public ResponseEntity<MessageResponse> checkTokenAction() {
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "success"));
    }
}
