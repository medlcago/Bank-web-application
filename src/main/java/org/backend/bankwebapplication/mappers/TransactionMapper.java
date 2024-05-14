package org.backend.bankwebapplication.mappers;

import org.backend.bankwebapplication.dto.responses.TransactionResponse;
import org.backend.bankwebapplication.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "senderUsername", source = "sender.username")
    @Mapping(target = "receiverUsername", source = "receiver.username")
    @Mapping(target = "amountWithCurrency", expression = "java(transaction.getAmount() + \" \" + transaction.getCurrency().getCode())")
    @Mapping(target = "type", expression = "java(transaction.getType().getDescription())")
    @Mapping (target="currency", expression = "java(transaction.getCurrency().getCode().name())")
    TransactionResponse transactionToTransactionResponse(Transaction transaction);

    List<TransactionResponse> transactionsToTransactionResponseList(List<Transaction> transactions);
}
