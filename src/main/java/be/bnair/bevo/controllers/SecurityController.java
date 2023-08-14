package be.bnair.bevo.controllers;

import be.bnair.bevo.models.forms.ErrorResponse;
import be.bnair.bevo.models.forms.RegisterForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import be.bnair.bevo.models.responses.AuthResponse;
import be.bnair.bevo.models.forms.LoginForm;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.JwtUtil;

@RestController
@RequestMapping(path = {"/auth"})
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
        UserDetails user = this.securityService.loadUserByUsername(form.getUsername());

        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping(path = {"/register"})
    public ResponseEntity<Object> registerAction(
            @RequestBody RegisterForm form
    ) {
        UserEntity entity = new UserEntity();
        entity.setNickname(form.getUsername());
        if(form.getPassword() != null && form.getConfirmPassword() != null && form.getPassword().equals(form.getConfirmPassword())) {
            entity.setPassword(passwordEncoder.encode(form.getPassword()));
            UserDetails user = this.securityService.create(entity);
            return ResponseEntity.ok(new AuthResponse(utils.generateToken(user)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Passwords do not match"));
        }
    }
}
