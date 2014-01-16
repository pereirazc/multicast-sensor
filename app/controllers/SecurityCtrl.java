package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import models.Auth;
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

    public F.Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
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
        return F.Promise.pure((SimpleResult) unauthorized("unauthorized"));
    }

    public static User getUser() {
        return (User) Http.Context.current().args.get("user");
    }

    public static FactHandle getAuth() {
        return (FactHandle) Http.Context.current().args.get("auth");
    }

    public static Result login() {
        JsonNode json = request().body().asJson();
        if(json != null) {
            User user = QueryHelper.getUserByMailAndPassword(SceneEngine.getInstance().getSession(),
                    json.findPath("email").asText(),
                    json.findPath("password").asText()
            );
            if (user != null) {
                Auth auth = new Auth(user);
                SceneEngine.getInstance().getSession().insert(auth);
                ObjectNode authTokenJson = Json.newObject();
                authTokenJson.put(AUTH_TOKEN, auth.getToken());
                response().setCookie(AUTH_TOKEN, auth.getToken());
                return ok(authTokenJson);
            }
            else return unauthorized("User not found");
        } else return badRequest("Expecting Json data");
    }

    @With(SecurityCtrl.class)
    public static Result logout() {
        response().discardCookie(AUTH_TOKEN);
        SceneEngine.getInstance().getSession().retract(SecurityCtrl.getAuth());
        return ok();
    }


}
