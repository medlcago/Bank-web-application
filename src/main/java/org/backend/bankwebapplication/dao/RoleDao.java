package org.backend.bankwebapplication.dao;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.entities.Role;
import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleDao {
    private final RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
