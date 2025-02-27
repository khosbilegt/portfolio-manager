package mn.khosbilegt.endpoint;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import mn.khosbilegt.service.UserService;
import mn.khosbilegt.service.user.UserDTO;
import mn.khosbilegt.util.Utilities;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/user")
public class UserEndpoint {
    @Inject
    UserService userService;
    @Inject
    JsonWebToken jwt;

    @GET
    @Authenticated
    public Uni<Response> fetchUser() {
        return userService.fetchUser(Integer.parseInt(jwt.getSubject()))
                .map(userDTO -> Response.ok(userDTO).build());
    }

    @POST
    @Path("/register")
    public Uni<Response> createUser(UserDTO userDTO) {
        return userService.createUser(userDTO)
                .map(jwt -> Response.ok(new JsonObject()
                        .put("token", jwt)
                ).build());
    }

    @POST
    @Path("/login")
    public Uni<Response> loginUser(UserDTO userDTO) {
        return userService.loginUser(userDTO)
                .map(jwt -> Response.ok(new JsonObject()
                        .put("token", jwt)
                ).build());
    }

    @PATCH
    @Path("/update")
    public Response updateUser(UserDTO userDTO) {
        userService.updateUser(jwt.getSubject(), userDTO);
        return Utilities.SUCCESS_RESPONSE;
    }

    @DELETE
    @Path("/delete")
    public Response deleteUser() {
        userService.deleteUser(jwt.getSubject());
        return Utilities.SUCCESS_RESPONSE;
    }
}
