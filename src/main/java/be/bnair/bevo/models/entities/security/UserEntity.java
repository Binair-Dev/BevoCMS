package be.bnair.bevo.models.entities.security;

import be.bnair.bevo.models.entities.*;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Cette classe représente une entité utilisateur dans le système.
 *
 * @author Brian Van Bellinghen
 */
@Entity(name = "User")
@Table(name = "bevo_users")
@Data
public class UserEntity extends AuditingBaseEntity implements UserDetails {
    /**
     * Le pseudo/nom d'utilisateur de l'utilisateur.
     */
    private String nickname;

    /**
     * L'adresse e-mail de l'utilisateur.
     */
    private String email;

    /**
     * Le mot de passe de l'utilisateur (haché ou encrypté).
     */
    private String password;

    /**
     * Indique si l'utilisateur a confirmé son compte.
     */
    private boolean isConfirmed;

    /**
     * Indique si le compte de l'utilisateur est activé.
     */
    private boolean isEnabled;

    /**
     * Le crédit de l'utilisateur.
     */
    private double credit;

    /**
     * Le rang (ou rôle) de l'utilisateur dans le système.
     */
    @ManyToOne
    @JoinColumn(name = "rank_id")
    private RankEntity rank;

    /**
     * Renvoie les autorisations (rôles) de l'utilisateur.
     *
     * @return Une collection d'objets GrantedAuthority représentant les autorisations de l'utilisateur.
     */
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authorities = new ArrayList<>();
        if (rank != null) {
            authorities.add("ROLE_" + rank.getTitle().toUpperCase());
        }
        authorities.add("ROLE_USER");

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * Renvoie le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Renvoie le nom d'utilisateur de l'utilisateur.
     *
     * @return Le nom d'utilisateur de l'utilisateur.
     */
    @Override
    public String getUsername() {
        return nickname;
    }

    /**
     * Indique si le compte de l'utilisateur a expiré.
     *
     * @return true si le compte n'a pas expiré, sinon false.
     */
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indique si le compte de l'utilisateur est verrouillé.
     *
     * @return true si le compte n'est pas verrouillé, sinon false.
     */
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indique si les informations d'identification de l'utilisateur ont expiré.
     *
     * @return true si les informations d'identification ne sont pas expirées, sinon false.
     */
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indique si le compte de l'utilisateur est activé.
     *
     * @return true si le compte est activé, sinon false.
     */
    @Transient
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
