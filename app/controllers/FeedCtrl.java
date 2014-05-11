package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import engine.SceneEngine;
import models.Feed;
import models.Sensor;
import models.User;
import org.drools.runtime.rule.QueryResultsRow;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import query.QueryHelper;

import static play.libs.Json.toJson;

@With(SecurityCtrl.class)
public class FeedCtrl extends Controller {

    //GET
    public static Result getFeed(String sensorId, String feedId) {
        User user = SecurityCtrl.getUser();
        QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (r != null) {
            Feed feed = (Feed) r.get("feed");
            return ok(toJson(feed)).as("application/json");
        } else return badRequest("Couldn't find Feed");
    }

    //POST
    public static Result createFeed(String sensorId) {
        User user = SecurityCtrl.getUser();
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
            if (r != null) {
                Sensor sensor = (Sensor) r.get("sensor");
                Feed feed = new Feed(sensor);
                if (json.has("feedId"))          feed.setFeedId(json.findPath("feedId").asText());
                if (json.has("description"))    feed.setDescription(json.findPath("description").asText());
                SceneEngine.getInstance().getSession().insert(feed);
                return ok(toJson(feed)).as("application/json");
            } else return badRequest("Couldn't find Sensor");
        } else return badRequest("Expecting Json data");
    }

    //POST
    public static Result updateFeed(String sensorId, String feedId) {
        User user = SecurityCtrl.getUser();
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
            if (r != null) {
                Feed feed = (Feed) r.get("feed");
                if (json.has("feedId"))          feed.setFeedId(json.findPath("feedId").asText());
                if (json.has("description"))    feed.setDescription(json.findPath("description").asText());
                SceneEngine.getInstance().getSession().update(r.getFactHandle("feed"), feed);
                JsonNode result = Json.parse((new Gson()).toJson(feed));
                return ok(result).as("application/json");
            } else return badRequest("Couldn't find Feed");
        } else return badRequest("Expecting Json data");
    }

    //DELETE
    public static Result deleteFeed(String sensorId, String feedId) {
        User user = SecurityCtrl.getUser();
        QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (r != null) {
            SceneEngine.getInstance().getSession().retract(r.getFactHandle("feed"));
            return ok().as("application/json");
        } else return badRequest("Couldn't find Feed");
    }

}
