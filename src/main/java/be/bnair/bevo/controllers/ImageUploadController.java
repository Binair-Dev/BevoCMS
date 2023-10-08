package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.responses.MessageResponse;
import be.bnair.bevo.services.UserService;
import be.bnair.bevo.utils.AuthUtils;
import be.bnair.bevo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contrôleur pour gérer l'upload et la récupération d'images liées aux skins des utilisateurs.
 * Ce contrôleur permet aux utilisateurs d'uploader leurs skins et de récupérer les skins existants.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/skins"})
public class ImageUploadController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du contrôleur ImageUploadController.
     *
     * @param jwtUtil     Utilitaire JWT pour la gestion des jetons.
     * @param userService Service utilisateur pour la gestion des utilisateurs.
     */
    public ImageUploadController(JwtUtil jwtUtil, UserService userService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Value("${upload.dir}")
    private String uploadDirectory;

    /**
     * Endpoint pour l'upload d'un skin d'utilisateur.
     *
     * @param file    Le fichier du skin à télécharger.
     * @param request La requête HTTP.
     * @return Une réponse indiquant le succès ou l'échec de l'upload du skin.
     */
    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadSkin(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Veuillez sélectionner un fichier à télécharger."));
        }

        UserEntity userDetails = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Impossible de récupèrer l'utilisateur via le token."));

        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = userDetails.getNickname() + "." + file.getOriginalFilename().split("\\.")[1];
            Path filePath = uploadPath.resolve(filename);

            FileCopyUtils.copy(file.getBytes(), filePath.toFile());
            return ResponseEntity.ok(new MessageResponse(HttpStatus.OK.value(), "Le skin a bien été upload"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le skin n'a pas pu être upload"));
        }
    }

    /**
     * Endpoint pour récupérer un skin par son nom de fichier.
     *
     * @param fileName Le nom du fichier skin à récupérer.
     * @return Une réponse contenant le contenu du skin.
     */
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<ByteArrayResource> getSkin(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDirectory, fileName);
            if(filePath.toFile().exists()) {
                byte[] fileContent = Files.readAllBytes(filePath);

                ByteArrayResource resource = new ByteArrayResource(fileContent);

                return ResponseEntity.ok()
                        .contentLength(fileContent.length)
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                filePath = Paths.get(uploadDirectory, "default.png");
                byte[] fileContent = Files.readAllBytes(filePath);

                ByteArrayResource resource = new ByteArrayResource(fileContent);

                return ResponseEntity.ok()
                        .contentLength(fileContent.length)
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
