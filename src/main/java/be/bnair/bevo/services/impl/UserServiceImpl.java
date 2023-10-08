package be.bnair.bevo.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.repository.RankRepository;
import be.bnair.bevo.repository.UserRepository;
import be.bnair.bevo.services.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des utilisateurs de l'application Bevo.
 * Cette classe implémente l'interface {@link UserService} et fournit les opérations de gestion des utilisateurs.
 *
 * @author Brian Van Bellinghen
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    /**
     * Constructeur de la classe UserServiceImpl.
     *
     * @param userRepository Le repository permettant l'accès aux données des utilisateurs.
     * @param rankRepository Le repository permettant l'accès aux données des rangs.
     */
    public UserServiceImpl(UserRepository userRepository, RankRepository rankRepository) {
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
    }

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à charger.
     * @return L'utilisateur chargé.
     * @throws UsernameNotFoundException Si aucun utilisateur avec le nom d'utilisateur donné n'est trouvé.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param entity L'objet {@link UserEntity} à créer.
     * @return L'utilisateur créé.
     */
    @Override
    public UserDetails create(UserEntity entity) {
        return this.userRepository.save(entity);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return Une liste d'objets {@link UserEntity}.
     */
    @Override
    public List<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant, ou {@link Optional#empty()} si aucun utilisateur n'est trouvé.
     */
    @Override
    public Optional<UserEntity> getOneById(Long id) {
        return this.userRepository.findById(id);
    }

    /**
     * Désactive un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à désactiver.
     * @throws Exception Si l'utilisateur n'est pas trouvé.
     */
    @Override
    public void remove(Long id) throws Exception {
        Optional<UserEntity> entity = this.userRepository.findById(id);
        if(entity.isPresent()) {
            UserEntity userEntity = entity.get();
            userEntity.setEnabled(false);
            this.userRepository.save(userEntity);
        }
    }

    /**
     * Met à jour un utilisateur par son identifiant.
     *
     * @param id      L'identifiant de l'utilisateur à mettre à jour.
     * @param updater L'objet {@link UserEntity} contenant les données de mise à jour.
     * @return L'utilisateur mis à jour.
     * @throws Exception Si l'utilisateur n'est pas trouvé.
     */
    @Override
    public UserEntity update(Long id, UserEntity updater) throws Exception {
        Optional<UserEntity> entity = this.userRepository.findById(id);
        if(entity.isPresent()) {
            UserEntity userEntity = entity.get();
            userEntity.setNickname(updater.getNickname());
            userEntity.setConfirmed(updater.isConfirmed());
            userEntity.setEnabled(updater.isEnabled());
            userEntity.setRank(updater.getRank());
            userEntity.setCredit(updater.getCredit());
            userEntity.setEmail(updater.getEmail());
            if(updater.getPassword() != null && updater.getPassword().length() > 0)
                userEntity.setPassword(updater.getPassword());
            return this.userRepository.save(userEntity);
        }
        throw new Exception("Could not find the user with id: " + id);
    }

    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant, ou {@link Optional#empty()} si aucun utilisateur n'est trouvé.
     */
    @Override
    public Optional<UserEntity> getOneByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
