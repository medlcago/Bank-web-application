package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.AccountDTO;
import org.backend.bankwebapplication.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "balanceWithCurrency", expression = "java(account.getBalance() + \" \" + account.getCurrency().getCode())")
    @Mapping(target = "currency", expression = "java(account.getCurrency().getCode().name())")
    AccountDTO accountToAccountDTO(Account account);

    List<AccountDTO> accountsToAccountDTOList(List<Account> accounts);
}
