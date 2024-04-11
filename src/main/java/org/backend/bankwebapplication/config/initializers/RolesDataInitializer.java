package org.backend.bankwebapplication.config.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.models.Role;
import org.backend.bankwebapplication.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RolesDataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing roles...");
        List<Role> roles = new ArrayList<>();
        if (!roleRepository.existsByName(ERole.ROLE_USER)) {
            Role role = Role.builder()
                    .name(ERole.ROLE_USER)
                    .build();
            roles.add(role);
        }
        if (!roles.isEmpty()) {
            roleRepository.saveAll(roles);
        }
    }
}