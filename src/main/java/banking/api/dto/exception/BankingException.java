package banking.api.dto.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BankingException extends WebApplicationException {

    public BankingException(String message, Response.Status status) throws IllegalArgumentException {
        super(message, status);
    }
}