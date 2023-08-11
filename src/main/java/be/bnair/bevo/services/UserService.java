package be.bnair.bevo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import be.bnair.bevo.models.entities.security.UserEntity;

public interface UserService extends UserDetailsService {
    UserDetails create(UserEntity entity);
    default void remove(Long id) throws Exception { throw new Exception(); }
    default UserEntity update(Long id, UserEntity updater) throws Exception { throw new Exception(); }
    List<UserEntity> getAll();
    Optional<UserEntity> getOneById(Long id);
}
