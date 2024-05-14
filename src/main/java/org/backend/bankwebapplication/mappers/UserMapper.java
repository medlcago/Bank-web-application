package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.responses.AccountResponse;
import org.backend.bankwebapplication.dto.responses.AboutMeResponse;
import org.backend.bankwebapplication.dto.responses.UserResponse;
import org.backend.bankwebapplication.entities.Account;
import org.backend.bankwebapplication.entities.Role;
import org.backend.bankwebapplication.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserResponse userToUserResponse(User user);

    default AboutMeResponse userToAboutMeResponse(User user) {
        UserResponse userResponse = userToUserResponse(user);
        List<AccountResponse> accountResponses = user.getAccounts().stream()
                .map(this::accountToAccountResponse)
                .collect(Collectors.toList());
        return new AboutMeResponse(userResponse, accountResponses);
    }

    default AccountResponse accountToAccountResponse(Account account) {
        return Mappers.getMapper(AccountMapper.class).accountToAccountResponse(account);
    }

    default String roleToString(Role role) {
        return Mappers.getMapper(RoleMapper.class).roleToString(role);
    }
}
