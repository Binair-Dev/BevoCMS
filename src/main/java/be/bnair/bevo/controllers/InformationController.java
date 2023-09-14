package be.bnair.bevo.controllers;

import be.bnair.bevo.models.Information;
import be.bnair.bevo.models.dto.UserDTO;
import be.bnair.bevo.utils.BevoUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bnair.bevo.services.UserService;

@RestController
@RequestMapping(value = "/infos")
public class InformationController {
    private final UserService userService;

    public InformationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"/stats"})
    public ResponseEntity<Information> getInformations() {
        long staff = this.userService.getAll().stream()
                .filter(user -> !user.getRank().getTitle().equals("Membre"))
                .map(UserDTO::toDTO).count();
        BevoUtils.view_count++;
        //TODO: use api to get acutal connected players from server
        return ResponseEntity.ok(new Information(this.userService.getAll().stream().count(), BevoUtils.view_count, 0, (int) staff));
    }
}