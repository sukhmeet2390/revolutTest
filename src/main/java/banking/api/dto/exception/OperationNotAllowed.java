package banking.api.dto.exception;

import javax.ws.rs.core.Response;

public class OperationNotAllowed extends BankingException {
    public OperationNotAllowed(String message) throws IllegalArgumentException {
        super(message, Response.Status.NOT_ACCEPTABLE);
    }
}

