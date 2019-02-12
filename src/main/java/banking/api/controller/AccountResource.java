package banking.api.controller;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/accounts")
@Produces(APPLICATION_JSON)
public interface AccountResource {
    @GET
    @Path("/{id}/statement")
    Response getStatement(
            @PathParam("id") Long id,
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("limit") Integer count);

    @GET
    @Path("/{id}")
    Response getBalance(@NotNull @PathParam("id") Long id);
}
