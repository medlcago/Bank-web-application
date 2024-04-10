package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.models.Role;
import org.backend.bankwebapplication.repository.RoleRepository;
import org.backend.bankwebapplication.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
