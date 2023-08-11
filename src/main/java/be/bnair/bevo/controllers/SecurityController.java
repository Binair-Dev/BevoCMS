package be.bnair.bevo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import be.bnair.bevo.models.AuthResponse;
import be.bnair.bevo.models.LoginForm;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.JwtUtil;

@RestController
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
    public ResponseEntity<AuthResponse> loginAction(
            HttpServletRequest request,
            @RequestBody LoginForm form
    ) {
        UserDetails user = this.securityService.loadUserByUsername(form.username);

        if (passwordEncoder.matches(form.password, user.getPassword())) {
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping(path = {"/register"})
    public ResponseEntity<AuthResponse> registerAction(
            @RequestBody LoginForm form
    ) {
        UserEntity entity = new UserEntity();
        entity.setNickname(form.username);
        entity.setPassword(passwordEncoder.encode(form.password));

        UserDetails user = this.securityService.insert(entity);
        return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
    }
}
