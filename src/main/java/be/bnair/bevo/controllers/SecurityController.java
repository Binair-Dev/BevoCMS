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

@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final JwtUtil utils;
    private final PasswordEncoder passwordEncoder;
    private final UserService securityService;
    private final RankService rankService;

    public SecurityController(JwtUtil utils, PasswordEncoder passwordEncoder, UserService securityService, RankService rankService) {
        this.utils = utils;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.rankService = rankService;
    }

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

    @GetMapping(path = "/authenticated")
    public ResponseEntity<MessageResponse> checkTokenAction() {
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "success"));
    }
}
