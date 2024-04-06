package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.ERole;
import org.backend.bankwebapplication.models.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);
}
