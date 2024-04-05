package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.TransactionDTO;
import org.backend.bankwebapplication.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "senderUsername", source = "sender.username")
    @Mapping(target = "receiverUsername", source = "receiver.username")
    TransactionDTO toDTO(Transaction transaction);

    List<TransactionDTO> toDTOList(List<Transaction> transactions);
}
