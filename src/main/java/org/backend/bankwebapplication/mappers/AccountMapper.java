package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.AccountDTO;
import org.backend.bankwebapplication.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO accountToAccountDTO(Account account);

    List<AccountDTO> accountsToAccountDTOList(List<Account> accounts);
}
