package banking.api.controller.impl;

import banking.api.controller.ProfileResource;
import banking.api.dto.request.CreateProfileRequest;
import banking.api.dto.response.Profile;
import banking.consumer.ProfileConsumer;
import com.google.inject.Inject;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

public class ProfileResourceImpl implements ProfileResource {
    private final ProfileConsumer profileConsumer;

    @Inject
    public ProfileResourceImpl(ProfileConsumer profileConsumer) {
        this.profileConsumer = profileConsumer;
    }


    @Override
    public Response createProfile(@Valid CreateProfileRequest request) {
        request.validate();
        Profile profile = profileConsumer.createUser(request.profile);
        return Response.status(201)
                .entity(profile)
                .build();
    }

    @Override
    public Response getProfileById(Long id) {
        return Response.status(200)
                .entity(profileConsumer.getUserById(id))
                .build();
    }
}
