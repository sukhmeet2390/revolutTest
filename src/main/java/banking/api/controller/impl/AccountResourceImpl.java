package banking.api.controller.impl;

import banking.api.controller.AccountResource;
import banking.api.dto.request.GetStatementRequest;
import banking.consumer.AccountConsumer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class AccountResourceImpl implements AccountResource {
    private final AccountConsumer accountConsumer;

    @Inject
    public AccountResourceImpl(AccountConsumer accountConsumer) {
        this.accountConsumer = accountConsumer;
    }

    @Override
    public Response getStatement(Long id, Long startTime, Long endTime, Integer count) {
        GetStatementRequest request = new GetStatementRequest(startTime, endTime, count, id);
        request.validate();
        return Response.status(200)
                .entity(accountConsumer.getStatement(
                        request.id,
                        request.startTime,
                        request.endTime,
                        request.count
                ))
                .build();
    }

    @Override
    public Response getBalance(Long id) {
        return Response.status(200)
                .entity(accountConsumer.getBalance(id))
                .build();
    }
}
