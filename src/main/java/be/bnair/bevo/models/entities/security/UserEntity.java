package be.bnair.bevo.models.entities.security;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import be.bnair.bevo.models.entities.AuditingBaseEntity;
import be.bnair.bevo.models.entities.RankEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "bevo_users")
@Data
public class UserEntity extends AuditingBaseEntity implements UserDetails {
    private String nickname;
    private String email;
    private String password;

    @OneToOne
    private RankEntity rank;
    private boolean isConfirmed;
    private boolean isEnabled;

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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}
