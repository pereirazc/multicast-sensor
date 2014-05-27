package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import models.AlertSituation;
import models.Data;
import models.Feed;
import models.User;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import query.QueryHelper;

import java.util.List;

import static play.libs.Json.toJson;

public class APICtrl extends Controller {

    //GET
    @With(SecurityCtrl.class)
    public static Result getStream(String sensorId, String feedId) {
        User user = SecurityCtrl.getUser();
        List<Data> stream = QueryHelper.GetFeedStream(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (stream != null) {
            ArrayNode result = JsonNodeFactory.instance.arrayNode();

            for(Data data: stream) {
                result.add(data.toJson());
            }
            return ok(result).as("application/json");
        } else return badRequest("Couldn't find Feed");
    }

    //GET
    @With(SecurityCtrl.class)
    public static Result getAlerts() {
        User user = SecurityCtrl.getUser();
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        QueryResults results = QueryHelper.getNotifications(SceneEngine.getInstance().getSession(), user.getUserId());
        if (results != null) {
            for(QueryResultsRow r: results) {
                JsonNode current = toJson(r.get("notification"));
                result.add(current);
            }
        }
        return ok(result).as("application/json");
    }

    //GET
    public static Result getAllAlerts() {
        User user = SecurityCtrl.getUser();
        ArrayNode result = JsonNodeFactory.instance.arrayNode();

        QueryResults results = QueryHelper.getAllNotifications(SceneEngine.getInstance().getSession());
        if (results != null) {

            for(QueryResultsRow r: results) {
                ObjectNode current =  ((AlertSituation) r.get("notification")).toJson();
                result.add(current);
            }
        }
        return ok(result).as("application/json");
    }

    //POST
    public static Result postData(String sensorId, String feedId) {
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getFeed(SceneEngine.getInstance().getSession(), sensorId, feedId);
            if (r != null) {
                Feed feed = (Feed) r.get("feed");
                Data data = new Data();
                if (json.has("timestamp")) {
                    data.setTimestamp(json.findPath("timestamp").asLong());
                } else {
                    data.setTimestamp(SceneEngine.getInstance().getSession().getSessionClock().getCurrentTime());
                }
                data.setValue(json.findPath("value").asDouble());
                data.setFeed(feed);
                SceneEngine.getInstance().getSession().insert(data);
                return ok().as("application/json");
            } else return badRequest("Couldn't find Feed");
        } else return badRequest("Expecting Json data");
    }

}
