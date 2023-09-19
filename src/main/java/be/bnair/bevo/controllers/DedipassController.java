package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.TransactionService;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/dedipass")
public class DedipassController {
    private final UserService userService;
    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;
    public DedipassController(JwtUtil jwtUtil, UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.jwtUtil = jwtUtil;
    }

    @Value("${dedipass.publicKey}")
    private String publicKey;

    @Value("${dedipass.privateKey}")
    private String privateKey;

    @PostMapping("/check")
    public void redirectToAngular(HttpServletResponse response, @RequestParam String code) throws IOException {
        System.out.println("REIDRECTIND TO SQOPJDI UPQSHND FQSIKOD N?JKS");
        response.sendRedirect("http://localhost:4200/status-code?code=" + code);
    }

    @GetMapping("/valid")
    public ResponseEntity<MessageResponse> validCodeAndAddCredit(
            HttpServletRequest request,
            @RequestParam String code) {
        UserEntity ue = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(ue == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Not authorized"));

        String json = checkAndAddCredit(code);
        try {
            JSONObject object = new JSONObject(json);
            String status = object.getString("status");
            double virtual_currency = object.getDouble("virtual_currency");
            double payout = object.getDouble("payout");
            if(status.equals("success")) {
                ue.setCredit(ue.getCredit() + virtual_currency);
                try {
                    this.userService.update(ue.getId(), ue);
                    TransactionEntity transactionEntity = new TransactionEntity();
                    transactionEntity.setCredit(virtual_currency);
                    transactionEntity.setPrice(payout);
                    transactionEntity.setDate(LocalDate.now());
                    transactionEntity.setUser(ue);
                    transactionEntity.setIdentifier(code);
                    this.transactionService.create(transactionEntity);
                    return ResponseEntity.ok(new MessageResponse(200, "Votre compte a été crédité de " + virtual_currency + " crédits!"));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(HttpStatus.NOT_FOUND.value(), "L'utilisateur n'existe pas!"));
                }
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Votre compte n'a pas pu être crédité car le code n'est pas valide!"));
            }
        } catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Impossible de récupèrer la réponse!"));
        }
    }

    private String checkAndAddCredit(String code) {
        String dedipassUrl = "http://api.dedipass.com/v1/pay/?public_key=" + publicKey + "&private_key=" + privateKey + "&code=" + code;
        RestTemplate restTemplate = new RestTemplate();
        String dedipassResponse = restTemplate.getForObject(dedipassUrl, String.class);
        return dedipassResponse;
    }
}
