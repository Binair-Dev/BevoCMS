package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.TransactionService;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/check")
    public ResponseEntity<Object> checkCode(HttpServletRequest request, @RequestParam String code) {

        UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));


        if(code.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous devez saisir un code");
        }

        String dedipassUrl = "http://api.dedipass.com/v1/pay/?public_key=" + publicKey +
                "&private_key=" + privateKey + "&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        String dedipassResponse = restTemplate.getForObject(dedipassUrl, String.class);

        try {
            JSONObject json = new JSONObject(dedipassResponse);
            String validity = json.getString("status");
            String rate = json.getString("rate");
            String payout = json.getString("payout");
            double virtual_currency = json.getDouble("virtual_currency");
            //TODO: save transaction

            //TODO: send credit to player
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(dedipassResponse);
    }
}
