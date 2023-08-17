package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import be.bnair.bevo.models.entities.security.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.nickname = :nickname")
    Optional<UserEntity> findByUsername(@Param("nickname") String nickname);

    @Query(value = "SELECT u FROM User u WHERE u.nickname = :nickname AND u.email = :email")
    Optional<UserEntity> findByUsernameAndEmail(@Param("nickname") String nickname, @Param("email") String email);
}
