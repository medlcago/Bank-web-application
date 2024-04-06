package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
