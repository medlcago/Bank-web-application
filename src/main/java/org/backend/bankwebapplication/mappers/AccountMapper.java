package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.responses.AccountResponse;
import org.backend.bankwebapplication.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "balanceWithCurrency", expression = "java(account.getBalance() + \" \" + account.getCurrency().getCode())")
    @Mapping(target = "currency", expression = "java(account.getCurrency().getCode().name())")
    AccountResponse accountToAccountResponse(Account account);

    List<AccountResponse> accountsToAccountResponseList(List<Account> accounts);
}
