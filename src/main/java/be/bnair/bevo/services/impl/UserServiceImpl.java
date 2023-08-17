package be.bnair.bevo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.repository.UserRepository;
import be.bnair.bevo.services.UserService;

@Service
public class UserServiceImpl implements UserService {
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
            userEntity.setConfirmed(updater.isConfirmed());
            userEntity.setEnabled(updater.isEnabled());
            userEntity.setNews(updater.getNews());
            userEntity.setTransactions(updater.getTransactions());
            userEntity.setVoteRewards(updater.getVoteRewards());
            userEntity.setRank(updater.getRank());
            userEntity.setShopTransactions(updater.getShopTransactions());
            return this.userRepository.save(userEntity);
        }
        throw new Exception("Could not find the user with id: " + id);
    }

    @Override
    public Optional<UserEntity> getOneByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
