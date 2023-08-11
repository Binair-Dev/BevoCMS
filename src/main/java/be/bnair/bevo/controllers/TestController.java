package be.bnair.bevo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class TestController {

    @GetMapping(path = "/test")
    public ResponseEntity getTestPage() {
        return ResponseEntity.ok().body("Success");
    }
}