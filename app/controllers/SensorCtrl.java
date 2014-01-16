package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import models.Feed;
import models.Sensor;
import models.User;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import query.QueryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static play.libs.Json.toJson;

@With(SecurityCtrl.class)
public class SensorCtrl extends Controller {
    //GET
    public static Result getAllSensors() {
        User user = SecurityCtrl.getUser();
        QueryResults results = QueryHelper.getAllSensors(SceneEngine.getInstance().getSession(), user.getUserId());
        List<Sensor> sensors = new ArrayList<>();
        for (QueryResultsRow r: results) {
            sensors.add((Sensor) r.get("sensor"));
        }
        return ok(toJson(sensors));
    }

    //GET
    public static Result getSensor(String sensorId) {
        User user = SecurityCtrl.getUser();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r != null) {

            Sensor sensor = (Sensor) r.get("sensor");
            JsonNode result = toJson(sensor);
            QueryResults results = QueryHelper.getAllFeeds(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
            List<Feed> feeds = new ArrayList<>();
            for (QueryResultsRow res: results) {
                feeds.add((Feed) res.get("feed"));
            }
            ((ObjectNode) result).put("feeds", toJson(feeds));
            return ok(result).as("application/json");
        } else return unauthorized("unauthorized");
    }

    //POST
    public static Result createSensor() {
        User user = SecurityCtrl.getUser();
        JsonNode json = request().body().asJson();
        if(json != null) {
            Sensor sensor = new Sensor(null);
            sensor.setOwner(user);
            sensor.setSensorId(UUID.randomUUID().toString());
            if (json.has("label"))          sensor.setLabel(json.findPath("label").asText());
            if (json.has("description"))    sensor.setDescription(json.findPath("description").asText());
            SceneEngine.getInstance().getSession().insert(sensor);
            return ok(toJson(sensor)).as("application/json");
        } else return badRequest("Expecting Json data");
    }

    //POST
    public static Result updateSensor(String sensorId) {
        User user = SecurityCtrl.getUser();
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
            if (r != null) {
                Sensor sensor = (Sensor) r.get("sensor");
                if (json.has("label"))          sensor.setLabel(json.findPath("label").asText());
                if (json.has("description"))    sensor.setDescription(json.findPath("description").asText());
                SceneEngine.getInstance().getSession().update(r.getFactHandle("sensor"), sensor);
                return ok(toJson(sensor)).as("application/json");
            } else return badRequest("Couldn't find Sensor");
        } else return badRequest("Expecting Json data");
    }

    public static Result deleteSensor(String sensorId) {
        User user = SecurityCtrl.getUser();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r != null) {
            SceneEngine.getInstance().getSession().retract(r.getFactHandle("sensor"));
            return ok();
        } else return badRequest("Couldn't find Sensor");
    }

    //POST
    public static Result generateNewKey(String sensorId) {
        User user = SecurityCtrl.getUser();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r != null) {
            Sensor sensor = (Sensor) r.get("sensor");
            sensor.setSensorId(UUID.randomUUID().toString());
            ObjectNode result = Json.newObject();
            //ObjectNode result = JsonNodeFactory.instance.objectNode();
            result.put("sensorId", sensor.getSensorId());
            SceneEngine.getInstance().getSession().update(r.getFactHandle("sensor"), sensor);
            return ok(result).as("application/json");
        } else return badRequest("Couldn't find Sensor");
    }

}
