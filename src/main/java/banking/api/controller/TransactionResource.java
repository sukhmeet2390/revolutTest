package banking.api.controller;

import banking.api.dto.request.AddBalanceRequest;
import banking.api.dto.request.DeductBalanceRequest;
import banking.api.dto.request.TransferBalanceRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/transactions")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface TransactionResource {

    @POST
    @Path("/add")
    Response addBalance(AddBalanceRequest addBalanceRequest);

    @POST
    @Path("/deduct")
    Response deductBalance(DeductBalanceRequest deductBalance);

    @POST
    @Path("/transfer")
    Response transferBalance(TransferBalanceRequest transferBalanceRequest);
}
