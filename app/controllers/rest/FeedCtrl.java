package controllers.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import controllers.SecurityCtrl;
import engine.SceneEngine;
import exception.NoUserException;
import exception.NotFoundException;
import models.Feed;
import models.Sensor;
import models.User;
import org.drools.runtime.rule.QueryResultsRow;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import query.QueryHelper;
import utils.FeedUtils;

import static play.libs.Json.toJson;

@With(SecurityCtrl.class)
public class FeedCtrl extends Controller {

    //GET
    public static Result getFeed(String sensorId, String feedId) {
        Feed feed;
        try {
            feed = FeedUtils.getFeed(SecurityCtrl.getUser(), sensorId, feedId);
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok(toJson(feed)).as("application/json");
    }

    //POST
    public static Result createFeed(String sensorId) {
        JsonNode json = request().body().asJson();
        if(json == null) return badRequest("Expecting Json data");
        Feed feed;
        try {
            feed = FeedUtils.createFeed(SecurityCtrl.getUser(),
                                        sensorId,
                                        json.findPath("feedId").asText(),
                                        json.findPath("description").asText());
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok(toJson(feed)).as("application/json");
    }

    //POST
    public static Result updateFeed(String sensorId, String feedId) {
        JsonNode json = request().body().asJson();
        if(json == null) return badRequest("Expecting Json data");
        Feed feed;
        try {
            feed = FeedUtils.updateFeed(SecurityCtrl.getUser(),
                                        sensorId,
                                        json.findPath("feedId").asText(),
                                        json.findPath("description").asText());
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok(toJson(feed)).as("application/json");
    }

    //DELETE
    public static Result deleteFeed(String sensorId, String feedId) {
        try {
            FeedUtils.deleteFeed(SecurityCtrl.getUser(), sensorId, feedId);
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok().as("application/json");
    }

}
