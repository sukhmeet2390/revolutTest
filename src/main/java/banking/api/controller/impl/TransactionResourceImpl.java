package banking.api.controller.impl;

import banking.api.controller.TransactionResource;
import banking.api.dto.request.AddBalanceRequest;
import banking.api.dto.request.DeductBalanceRequest;
import banking.api.dto.request.TransferBalanceRequest;
import banking.api.dto.response.Balance;
import banking.consumer.AccountConsumer;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

@Singleton
public class TransactionResourceImpl implements TransactionResource {
    private final AccountConsumer accountConsumer;

    @Inject
    public TransactionResourceImpl(AccountConsumer accountConsumer) {
        this.accountConsumer = accountConsumer;
    }

    @Override
    public Response addBalance(AddBalanceRequest addBalanceRequest) {
        addBalanceRequest.validate();
        Balance balance = accountConsumer.addBalance(
                addBalanceRequest.userId,
                addBalanceRequest.amount
        );
        return Response.status(200)
                .entity(balance)
                .build();
    }

    @Override
    public Response deductBalance(DeductBalanceRequest deductBalanceRequest) {
        deductBalanceRequest.validate();
        Balance balance = accountConsumer.deductBalance(
                deductBalanceRequest.userId,
                deductBalanceRequest.amount
        );
        return Response.status(200)
                .entity(balance)
                .build();
    }

    @Override
    public Response transferBalance(TransferBalanceRequest transferBalanceRequest) {
        transferBalanceRequest.validate();
        Balance balance = accountConsumer.transferMoney(
                transferBalanceRequest.fromUserId,
                transferBalanceRequest.toUserId,
                transferBalanceRequest.amount
        );
        return Response.status(200)
                .entity(balance)
                .build();
    }
}
