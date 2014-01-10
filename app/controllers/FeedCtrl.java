package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import engine.SceneEngine;
import models.Data;
import models.Feed;
import models.Sensor;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import query.QueryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FeedCtrl extends Controller {

    //GET
    public static Result getFeed(String sensorId, String feedId) {

        QueryResultsRow r = QueryHelper.getFeed(SceneEngine.getInstance().getSession(), sensorId, feedId);
        if (r != null) {
            Gson gson = new Gson();
            Feed feed = (Feed) r.get("feed");
            JsonNode result = Json.parse(gson.toJson(feed));
            return ok(result).as("application/json");
        } else return badRequest("Couldn't find Feed");

    }

    //POST
    public static Result createFeed(String sensorId) {
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), sensorId);
            if (r != null) {
                Sensor sensor = (Sensor) r.get("sensor");
                Feed feed = new Feed();
                //feed.setFeedId(UUID.randomUUID().toString());
                feed.setSensor(sensor);
                if (json.has("feedId"))          feed.setFeedId(json.findPath("feedId").asText());
                if (json.has("description"))    feed.setDescription(json.findPath("description").asText());
                SceneEngine.getInstance().getSession().insert(feed);
                //ObjectNode result = JsonNodeFactory.instance.objectNode();
                //result.put("feedId", feed.getFeedId());

                JsonNode result = Json.parse((new Gson()).toJson(feed));

                return ok(result).as("application/json");
            } else return badRequest("Couldn't find Sensor");
        } else return badRequest("Expecting Json data");
    }

    //POST
    public static Result updateFeed(String sensorId, String feedId) {
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getFeed(SceneEngine.getInstance().getSession(), sensorId, feedId);
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
        QueryResultsRow r = QueryHelper.getFeed(SceneEngine.getInstance().getSession(), sensorId, feedId);
        if (r != null) {
            SceneEngine.getInstance().getSession().retract(r.getFactHandle("feed"));
            return ok().as("application/json");
        } else return badRequest("Couldn't find Feed");
    }

}
