package mn.khosbilegt.util;

import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.core.Response;

public class Utilities {
    public static Response SUCCESS_RESPONSE = Response.ok(new JsonObject()
            .put("success", "SUCCESS")
    ).build();
}
