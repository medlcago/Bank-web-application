package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    default String roleToString(Role role) {
        return role.getName().name();
    }

    default List<String> roleSetToStringList(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }
}
