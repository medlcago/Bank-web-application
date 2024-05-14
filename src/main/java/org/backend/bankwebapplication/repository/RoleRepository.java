package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    boolean existsByName(ERole name);
}
