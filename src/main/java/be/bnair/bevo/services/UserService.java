package be.bnair.bevo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import be.bnair.bevo.models.entities.security.UserEntity;

/**
 * Interface de service pour la gestion des utilisateurs de l'application.
 * Cette interface étend {@link UserDetailsService} pour fournir des opérations spécifiques aux utilisateurs.
 *
 * @author Brian Van Bellinghen
 */
public interface UserService extends UserDetailsService {
    /**
     * Crée un nouvel utilisateur.
     *
     * @param entity L'entité utilisateur à créer.
     * @return Les détails de l'utilisateur créé.
     */
    UserDetails create(UserEntity entity);

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     * @throws Exception si une erreur se produit lors de la suppression.
     */
    default void remove(Long id) throws Exception {
        throw new Exception();
    }

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id      L'identifiant de l'utilisateur à mettre à jour.
     * @param updater L'entité utilisateur mise à jour.
     * @return L'entité utilisateur mise à jour.
     * @throws Exception si une erreur se produit lors de la mise à jour.
     */
    default UserEntity update(Long id, UserEntity updater) throws Exception {
        throw new Exception();
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs.
     */
    List<UserEntity> getAll();

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant à l'identifiant, s'il existe.
     */
    Optional<UserEntity> getOneById(Long id);

    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant au nom d'utilisateur, s'il existe.
     */
    Optional<UserEntity> getOneByUsername(String username);
}
