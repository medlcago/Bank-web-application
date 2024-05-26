package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dao.RoleDao;
import org.backend.bankwebapplication.entities.Role;
import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleDao.findByName(name);
    }
}
