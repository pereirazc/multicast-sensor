package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import models.*;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import query.QueryHelper;

import java.util.List;

import static play.libs.Json.toJson;

public class APICtrl extends Controller {

    //POST
    @With(SecurityCtrl.class)
    public static Result markAsDelivered() {

        JsonNode json = request().body().asJson();
        if(json == null) return badRequest("Expecting Json data");
        if (!json.has("device")) return badRequest("device not specified");
        QueryResultsRow r = QueryHelper.getDevice( SceneEngine.getInstance().getSession(), SecurityCtrl.getUser().getUserId(), json.findPath("device").asText());
        if (r == null) return badRequest("device not found!");
        Device device = (Device) r.get("device");
        ArrayNode notifications = (ArrayNode) json.get("notifications");
        Notification notification;
        for(JsonNode notficationNode: notifications) {
            r = QueryHelper.getNotification( SceneEngine.getInstance().getSession(), SecurityCtrl.getUser().getUserId(), notficationNode.asLong());
            if (r == null) return badRequest("notification not found!");
            notification = (Notification) r.get("notification");
            SceneEngine.getInstance().getSession().insert(new Delivered(device, notification));
        }
        return ok().as("application/json");
    }

    //GET
    public static Result getDeliveredNotifications() {
        List<Delivered> data = QueryHelper.getDeliveredNotifications(SceneEngine.getInstance().getSession());
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        for(Delivered d: data) {
            result.add(d.asJson());
        }
        return ok(result).as("application/json");
    }

    //GET
    public static Result getRegisteredDevices() {
        List<Device> data = QueryHelper.getRegisteredDevices(SceneEngine.getInstance().getSession());
        return ok(toJson(data)).as("application/json");
    }

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
    public static Result getAlerts(String deviceId) {
        User user = SecurityCtrl.getUser();
        if (deviceId == null) return badRequest("device not specified");
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        QueryResults results = QueryHelper.getNotifications(SceneEngine.getInstance().getSession(), user.getUserId(), deviceId);
        if (results != null) {
            for(QueryResultsRow n: results) {
                JsonNode current = ((Notification) n.get("notification")).toJson();
                result.add(current);
            }
        }
        return ok(result).as("application/json");
    }

    //GET
    public static Result getAllAlerts(Long min) { User user = SecurityCtrl.getUser();
        ArrayNode result = JsonNodeFactory.instance.arrayNode();

        QueryResults results = QueryHelper.getAllNotifications(SceneEngine.getInstance().getSession(), min);
        if (results != null) {

            for(QueryResultsRow r: results) {
                ObjectNode current =  ((Notification) r.get("notification")).toJson();
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
