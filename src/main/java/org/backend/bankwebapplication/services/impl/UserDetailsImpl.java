package org.backend.bankwebapplication.services.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.backend.bankwebapplication.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private User user;

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastname() {
        return user.getLastName();
    }

    public String getCreatedAt() {
        return user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss"));
    }

    public String getFullName() {
        return String.join(" ", user.getFirstName(), user.getLastName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user.getId() == null) ? 0 : user.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDetailsImpl other = (UserDetailsImpl) obj;
        if (user.getId() == null) {
            return other.user.getId() == null;
        } else return user.getId().equals(other.user.getId());
    }
}
