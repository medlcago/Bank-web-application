package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);
}
