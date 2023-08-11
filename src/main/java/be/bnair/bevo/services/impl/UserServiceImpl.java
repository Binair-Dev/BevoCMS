package be.bnair.bevo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.repository.UserRepository;
import be.bnair.bevo.services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails create(UserEntity entity) {
        return this.userRepository.save(entity);
    }

    @Override
    public List<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getOneById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        Optional<UserEntity> entity = this.userRepository.findById(id);
        if(entity.isPresent()) {
            UserEntity userEntity = entity.get();
            userEntity.setEnabled(false);
            this.userRepository.save(userEntity);
        }
    }

    @Override
    public UserEntity update(Long id, UserEntity updater) throws Exception {
        Optional<UserEntity> entity = this.userRepository.findById(id);
        if(entity.isPresent()) {
            UserEntity userEntity = entity.get();
            userEntity.setEmail(updater.getEmail());
            userEntity.setPassword(updater.getPassword());
            userEntity.setRank(updater.getRank());
            userEntity.setConfirmed(updater.isConfirmed());
            userEntity.setEnabled(updater.isEnabled());
            return this.userRepository.save(userEntity);
        }
        return null;
    }
}
