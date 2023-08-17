package be.bnair.bevo.controllers;

import be.bnair.bevo.models.forms.RegisterForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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

    public SecurityController(JwtUtil utils, PasswordEncoder passwordEncoder, UserService securityService) {
        this.utils = utils;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
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
        }

        UserDetails user = this.securityService.loadUserByUsername(form.getUsername());

        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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

        Optional<UserEntity> exist = this.securityService.getOneByUsername(form.getUsername());
        if(exist.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le nom d'utilisateur est déjà utilisé."));
        }

        UserEntity entity = new UserEntity();
        entity.setNickname(form.getUsername());
        if(form.getPassword() != null && form.getConfirmPassword() != null && form.getPassword().equals(form.getConfirmPassword())) {
            entity.setPassword(passwordEncoder.encode(form.getPassword()));
            
            UserDetails user = this.securityService.create(entity);
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Les mots de passes ne correspondent pas."));
        }
    }
}
