package banking.mappers;

import banking.api.dto.response.Transaction;
import banking.dao.domain.TransactionDO;

import java.util.Date;

public class TransactionMapper {
    public static Transaction transactionDoToDto(TransactionDO transactionDO) {
        return new Transaction(transactionDO.getId(),
                transactionDO.getFrom(),
                transactionDO.getTo(),
                new Date(transactionDO.getTime()),
                transactionDO.getAmount(),
                transactionDO.getTransactionType(),
                transactionDO.getTotalBalance());
    }
}