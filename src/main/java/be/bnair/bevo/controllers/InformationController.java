package be.bnair.bevo.controllers;

import be.bnair.bevo.models.Information;
import be.bnair.bevo.models.dto.UserDTO;
import be.bnair.bevo.utils.BevoUtils;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bnair.bevo.models.dto.NewsDTO;
import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.forms.NewsForm;
import be.bnair.bevo.models.responses.FieldErrorResponse;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.NewsService;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.AuthUtils;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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