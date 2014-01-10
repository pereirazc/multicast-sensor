package controllers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

public class SensorCtrl extends Controller {

    //GET
    public static Result getAllSensors() {

        Gson gson = new Gson();
        QueryResults results = QueryHelper.getAllSensors(SceneEngine.getInstance().getSession());
        List<Sensor> sensors = new ArrayList<>();
        for (QueryResultsRow r: results) {
            sensors.add((Sensor) r.get("sensor"));
        }

        String s = gson.toJson(sensors);
        JsonNode result = Json.parse(s);
        return ok(result).as("application/json");
    }

    //GET
    public static Result getSensor(String sensorId) {

        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), sensorId);
        if (r != null) {
            Gson gson = new Gson();
            Sensor sensor = (Sensor) r.get("sensor");

            JsonNode result = Json.parse(gson.toJson(sensor));

            QueryResults results = QueryHelper.getAllFeeds(SceneEngine.getInstance().getSession(), sensorId);
            List<Feed> feeds = new ArrayList<>();
            for (QueryResultsRow res: results) {
                feeds.add((Feed) res.get("feed"));
            }
            String s = gson.toJson(feeds);
            ((ObjectNode) result).put("feeds", Json.parse(s));

            return ok(result).as("application/json");
        } else return badRequest("Couldn't find Sensor");

    }

    //POST
    public static Result createSensor() {
        JsonNode json = request().body().asJson();
        if(json != null) {
            Sensor sensor = new Sensor();
            sensor.setSensorId(UUID.randomUUID().toString());
            if (json.has("label"))          sensor.setLabel(json.findPath("label").asText());
            if (json.has("description"))    sensor.setDescription(json.findPath("description").asText());
            SceneEngine.getInstance().getSession().insert(sensor);
            //ObjectNode result = JsonNodeFactory.instance.objectNode();
            //result.put("sensorId", sensor.getSensorId());

            JsonNode result = Json.parse((new Gson()).toJson(sensor));

            return ok(result).as("application/json");
        } else return badRequest("Expecting Json data");
    }

    //POST
    public static Result updateSensor(String sensorId) {
        JsonNode json = request().body().asJson();
        if(json != null) {
            QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), sensorId);
            if (r != null) {
                Sensor sensor = (Sensor) r.get("sensor");
                if (json.has("label"))          sensor.setLabel(json.findPath("label").asText());
                if (json.has("description"))    sensor.setDescription(json.findPath("description").asText());
                SceneEngine.getInstance().getSession().update(r.getFactHandle("sensor"), sensor);

                JsonNode result = Json.parse((new Gson()).toJson(sensor));

                return ok(result).as("application/json");
            } else return badRequest("Couldn't find Sensor");
        } else return badRequest("Expecting Json data");
    }

    public static Result deleteSensor(String sensorId) {
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), sensorId);
        if (r != null) {
            SceneEngine.getInstance().getSession().retract(r.getFactHandle("sensor"));
            return ok();
        } else return badRequest("Couldn't find Sensor");
    }

    //POST
    public static Result generateNewKey(String sensorId) {

        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), sensorId);
        if (r != null) {
            Sensor sensor = (Sensor) r.get("sensor");

            sensor.setSensorId(UUID.randomUUID().toString());
            ObjectNode result = JsonNodeFactory.instance.objectNode();
            result.put("sensorId", sensor.getSensorId());

            SceneEngine.getInstance().getSession().update(r.getFactHandle("sensor"), sensor);
            return ok(result).as("application/json");
        } else return badRequest("Couldn't find Sensor");
    }

}
