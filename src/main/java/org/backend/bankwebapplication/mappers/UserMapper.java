package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.AboutMeDTO;
import org.backend.bankwebapplication.dto.AccountDTO;
import org.backend.bankwebapplication.dto.UserDTO;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.Role;
import org.backend.bankwebapplication.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserDTO userToUserDTO(User user);

    default AboutMeDTO userToAboutMeDTO(User user) {
        UserDTO userDTO = userToUserDTO(user);
        List<AccountDTO> accountDTOs = user.getAccounts().stream()
                .map(this::accountToAccountDTO)
                .collect(Collectors.toList());
        return new AboutMeDTO(userDTO, accountDTOs);
    }

    default AccountDTO accountToAccountDTO(Account account) {
        return Mappers.getMapper(AccountMapper.class).accountToAccountDTO(account);
    }

    default String roleToString(Role role) {
        return Mappers.getMapper(RoleMapper.class).roleToString(role);
    }
}
