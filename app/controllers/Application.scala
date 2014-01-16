package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import engine.SceneEngine

/** Application controller, handles authentication */
object Application extends Controller {

  /** Serves the index page, see views/index.scala.html */
  def index = Action {
    SceneEngine.getInstance()
    Ok(views.html.index())
  }

  /**
   * Returns the JavaScript router that the client can use for "type-safe" routes.
   * @param varName The name of the global variable, defaults to `jsRoutes`
   */
  def jsRoutes(varName: String = "jsRoutes") = Action { implicit request =>
    Ok(
      Routes.javascriptRouter(varName)(
        //routes.javascript.Application.login,
        //routes.javascript.Application.logout,

        routes.javascript.SecurityCtrl.login,
        routes.javascript.SecurityCtrl.logout,
        routes.javascript.UserCtrl.getUser,
        routes.javascript.SensorCtrl.getAllSensors,
        routes.javascript.SensorCtrl.getSensor,
        routes.javascript.SensorCtrl.createSensor,
        routes.javascript.SensorCtrl.updateSensor,
        routes.javascript.SensorCtrl.generateNewKey,
        routes.javascript.SensorCtrl.deleteSensor,
        routes.javascript.FeedCtrl.getFeed,
        routes.javascript.FeedCtrl.createFeed,
        routes.javascript.FeedCtrl.updateFeed,
        routes.javascript.FeedCtrl.deleteFeed,
        routes.javascript.APICtrl.getStream,
        routes.javascript.APICtrl.postData

        // TODO Add your routes here
      )
    ).as(JAVASCRIPT)
  }

  /**
   * Log-in a user. Pass the credentials as JSON body.
   * @return The token needed for subsequent requests
   */
  def login() = Action(parse.json) { implicit request =>
    // TODO Check credentials, log user in, return correct token
    val token = java.util.UUID.randomUUID().toString
    Ok(Json.obj("token" -> token))
  }

  /** Logs the user out, i.e. invalidated the token. */
  def logout() = Action {
    // TODO Invalidate token, remove cookie
    Ok
  }

}
