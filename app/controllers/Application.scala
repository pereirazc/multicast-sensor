package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current
import play.api.cache.Cached

/** Application controller, handles authentication */
object Application extends Controller {

  /** Serves the index page, see views/index.scala.html */
  def index = Action {
    Ok(views.html.index())
  }

  def error = Action {
    Ok(views.html.error.render())
  }

  def redirectToAngular(path: String) = Action {
    Redirect("/#/" + path);
    //Ok(views.html.error.render())
  }

  /**
   * Returns the JavaScript router that the client can use for "type-safe" routes.
   * Uses browser caching; set duration (in seconds) according to your release cycle.
   * @param varName The name of the global variable, defaults to `jsRoutes`
   */
  def jsRoutes(varName: String = "jsRoutes") = Cached(_ => "jsRoutes", duration = 86400) {
    Action { implicit request =>
      Ok(
        Routes.javascriptRouter(varName)(
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
          routes.javascript.APICtrl.postData,
          routes.javascript.Application.error
          // TODO Add your routes here
        )
      ).as(JAVASCRIPT)
    }
  }

}
