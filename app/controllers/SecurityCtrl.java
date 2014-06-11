package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import models.Auth;
import models.Device;
import models.User;
import org.drools.FactHandle;
import org.drools.runtime.rule.QueryResultsRow;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;
import query.QueryHelper;

import static play.mvc.Controller.request;
import static play.mvc.Controller.response;

public class SecurityCtrl extends Action.Simple {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        User user = null;
        String[] authTokenHeaderValues = ctx.request().headers().get(AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {

            QueryResultsRow r = QueryHelper.getUserByToken(SceneEngine.getInstance().getSession(), authTokenHeaderValues[0]);

            if (r != null) {
                user = (User) r.get("user");
                if (user != null) {
                    ctx.args.put("user", user);
                    ctx.args.put("auth",  r.getFactHandle("auth"));
                    return delegate.call(ctx);
                }
            }
        }
        return F.Promise.pure((Result) unauthorized("unauthorized"));
    }

    public static User getUser() {
        return (User) Http.Context.current().args.get("user");
    }

    public static FactHandle getAuth() {
        return (FactHandle) Http.Context.current().args.get("auth");
    }

    public static Result login() {
        JsonNode json = request().body().asJson();
        if(json == null) return badRequest("Expecting Json data");
        User user = QueryHelper.getUserByMailAndPassword(SceneEngine.getInstance().getSession(),
                json.findPath("email").asText(),
                json.findPath("password").asText()
        );
        if (user == null) return unauthorized("User not found");
        Auth auth = new Auth(user);
        SceneEngine.getInstance().getSession().insert(auth);
        ObjectNode authTokenJson = Json.newObject();
        authTokenJson.put(AUTH_TOKEN, auth.getToken());
        response().setCookie(AUTH_TOKEN, auth.getToken());

        if (json.has("device")) {
            QueryResultsRow r = QueryHelper.getDevice( SceneEngine.getInstance().getSession(), SecurityCtrl.getUser().getUserId(), json.findPath("device").asText());
            if (r != null) {
                Device device = (Device) r.get("device");
                SceneEngine.getInstance().getSession().insert(new Device(user, json.findPath("device").asText()));
            }
        }
        return ok(authTokenJson);

    }

    @With(SecurityCtrl.class)
    public static Result logout() {
        response().discardCookie(AUTH_TOKEN);
        SceneEngine.getInstance().getSession().retract(SecurityCtrl.getAuth());
        return ok();
    }


}
