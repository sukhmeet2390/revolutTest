package banking.api.controller;

import banking.api.dto.request.CreateProfileRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/profiles")
@Produces(APPLICATION_JSON)

public interface ProfileResource {
    @GET
    @Path("/{id}")
    Response getProfileById(@PathParam("id") Long id);

    @POST
    @Path("/")
    Response createProfile(@Valid CreateProfileRequest createProfileRequest);
}
