package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.Role;
import org.backend.bankwebapplication.models.Roles;

public interface RoleService {
    Role findByName(String name);

    Role findByRole(Roles role);
}
