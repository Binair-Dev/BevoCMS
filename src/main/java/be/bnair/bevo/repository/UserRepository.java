package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.security.UserEntity;

import java.util.Optional;

/**
 * Interface de repository pour la gestion des entités utilisateur (UserEntity).
 * Cette interface permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les entités utilisateur en utilisant Spring Data JPA.
 *
 * @author Brian Van Bellinghen
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Recherche un utilisateur par son pseudonyme (nickname).
     *
     * @param nickname Le pseudonyme de l'utilisateur à rechercher.
     * @return Une option contenant l'utilisateur correspondant s'il existe.
     */
    @Query(value = "SELECT u FROM User u WHERE u.nickname = :nickname")
    Optional<UserEntity> findByUsername(@Param("nickname") String nickname);

    /**
     * Recherche un utilisateur par son pseudonyme (nickname) et son adresse e-mail.
     *
     * @param nickname Le pseudonyme de l'utilisateur à rechercher.
     * @param email    L'adresse e-mail de l'utilisateur à rechercher.
     * @return Une option contenant l'utilisateur correspondant s'il existe.
     */
    @Query(value = "SELECT u FROM User u WHERE u.nickname = :nickname AND u.email = :email")
    Optional<UserEntity> findByUsernameAndEmail(@Param("nickname") String nickname, @Param("email") String email);
}
