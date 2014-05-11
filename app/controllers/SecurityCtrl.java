package controllers;

import controllers.routes;
import play.data.DynamicForm;
import play.data.Form;
import play.data.Form.*;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import models.Auth;
import models.User;
import org.drools.FactHandle;
import org.drools.runtime.rule.QueryResultsRow;
import play.libs.F.*;
import play.libs.Json;
import play.mvc.*;
import query.QueryHelper;
import views.html.defaultpages.error;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.request;
import static play.mvc.Controller.response;

public class SecurityCtrl extends Action.Simple {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
        String auth = Controller.session().get("auth");
        if (auth == null) return Promise.pure((SimpleResult) unauthorized("unauthorized"));

        QueryResultsRow r = QueryHelper.getUserByToken(SceneEngine.getInstance().getSession(), auth);
        if (r != null) {
            User user = (User) r.get("user");
            if (user != null) {
                ctx.args.put("user", user);
                ctx.args.put("auth",  r.getFactHandle("auth"));
                return delegate.call(ctx);
            } else {
                Controller.session().clear();
                return Promise.pure((SimpleResult) unauthorized("user not found"));
            }
        }
        else {
            Controller.session().clear();
            return Promise.pure((SimpleResult) unauthorized("invalid token"));
        }
    }

    public static User getUser() {
        return (User) Http.Context.current().args.get("user");
    }

    public static FactHandle getAuth() {
        return (FactHandle) Http.Context.current().args.get("auth");
    }

    public static Result loginIndex() {
        return ok(views.html.login.render());
    }

    public static Result signupIndex() {
        return ok(views.html.login.render());
    }

    public static Result login() {

        DynamicForm loginForm = Form.form().bindFromRequest();
        String email = loginForm.get("email");
        String password = loginForm.get("password");
        User user = QueryHelper.getUserByMailAndPassword(SceneEngine.getInstance().getSession(),email,password);

        if (user == null) return ok();

        Auth auth = new Auth(user);
        SceneEngine.getInstance().getSession().insert(auth);
        //ObjectNode authTokenJson = Json.newObject();
        //authTokenJson.put(AUTH_TOKEN, auth.getToken());
        Controller.session().put("auth", auth.getToken());
        Controller.session().put("firstName", user.getFirstName());
        Controller.session().put("lastName", user.getLastName());
        flash("message", "successful login");
        return redirect(controllers.routes.Application.index());
    }

    @With(SecurityCtrl.class)
    public static Result logout() {
        Controller.session().clear();
        flash ("message", "successful logout");
        //response().discardCookie(AUTH_TOKEN);
        SceneEngine.getInstance().getSession().retract(SecurityCtrl.getAuth());
        return redirect(controllers.routes.Application.index());
    }


}
