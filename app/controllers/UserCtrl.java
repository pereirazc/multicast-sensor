package controllers;

import controllers.SecurityCtrl;import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import static play.libs.Json.toJson;

public class UserCtrl extends Controller {

    @With(SecurityCtrl.class)
    public static Result getUser() {
        return ok(toJson(SecurityCtrl.getUser()));
    }

}
