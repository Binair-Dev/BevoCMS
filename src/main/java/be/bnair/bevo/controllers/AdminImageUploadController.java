package be.bnair.bevo.controllers;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.models.responses.ImageResponse;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur pour gérer l'upload, la récupération et la suppression d'images par l'administrateur.
 * Ce contrôleur gère les opérations liées aux images, y compris l'upload, la récupération et la suppression.
 *
 * @author Brian Van Bellinghen
 */
@RestController
@RequestMapping(path = {"/images"})
public class AdminImageUploadController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du contrôleur AdminImageUploadController.
     *
     * @param jwtUtil     Utilitaire JWT pour la gestion des jetons.
     * @param userService Service utilisateur pour la gestion des utilisateurs.
     */
    public AdminImageUploadController(JwtUtil jwtUtil, UserService userService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Value("${upload.admin.dir}")
    private String uploadDirectory;
    @Value("${upload.url}")
    private String baseUrl;

    /**
     * Endpoint pour l'upload d'une image par l'administrateur.
     *
     * @param file    Le fichier image à télécharger.
     * @param request La requête HTTP.
     * @return Une réponse indiquant le succès ou l'échec de l'upload.
     */
    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        UserEntity user = AuthUtils.getUserDetailsFromToken(request, jwtUtil, userService);
        if(user.getRank().getId() != 0) {

        }
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

            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            FileCopyUtils.copy(file.getBytes(), filePath.toFile());
            return ResponseEntity.ok(new MessageResponse(HttpStatus.OK.value(), "Le skin a bien été upload"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Le skin n'a pas pu être upload"));
        }
    }

    /**
     * Endpoint pour récupérer toutes les images.
     *
     * @return Une réponse contenant la liste des URL des images disponibles.
     */
    @GetMapping("/get")
    public ResponseEntity<ImageResponse> getAll() {
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {}
        List<String> images = new ArrayList<String>();
        Path filePath = Paths.get(uploadDirectory);
        for(File f : filePath.toAbsolutePath().toFile().listFiles()) {
            if(f.exists() && !f.isDirectory()) {
                images.add(this.baseUrl + "/" + f.getName());
            }
        }
        return ResponseEntity.ok(new ImageResponse(images));
    }

    /**
     * Endpoint pour récupérer une image par son nom de fichier.
     *
     * @param fileName Le nom du fichier image à récupérer.
     * @return Une réponse contenant le contenu de l'image.
     */
    @GetMapping("/get/{fileName:.+}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDirectory, fileName);
            byte[] fileContent = Files.readAllBytes(filePath);

            ByteArrayResource resource = new ByteArrayResource(fileContent);

            return ResponseEntity.ok()
                    .contentLength(fileContent.length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint pour supprimer une image par son nom de fichier.
     *
     * @param fileName Le nom du fichier image à supprimer.
     * @return Une réponse indiquant le succès ou l'échec de la suppression de l'image.
     */
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<Object> deleteImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDirectory, fileName);
            Files.delete(filePath);
            return ResponseEntity.ok(null);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
