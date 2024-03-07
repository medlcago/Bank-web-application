package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.ERole;
import org.backend.bankwebapplication.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}