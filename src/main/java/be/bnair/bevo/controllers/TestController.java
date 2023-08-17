package be.bnair.bevo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import be.bnair.bevo.models.responses.MessageResponse;

@RestController
@RequestMapping("/admin")
public class TestController {
    @GetMapping(path = "/test")
    public ResponseEntity<MessageResponse> getTestPage() {
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.ACCEPTED.value(), "Validation réussie, vous êtes bien connecté et administrateur."));
    }
}