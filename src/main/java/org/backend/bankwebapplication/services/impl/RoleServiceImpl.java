package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Role;
import org.backend.bankwebapplication.models.Roles;
import org.backend.bankwebapplication.repository.RoleRepository;
import org.backend.bankwebapplication.services.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role findByRole(Roles role) {
        String roleName = role.name();
        return findByName(roleName);
    }
}
