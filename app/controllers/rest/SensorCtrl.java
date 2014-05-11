package controllers.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.SecurityCtrl;
import engine.SceneEngine;
import exception.NoUserException;
import exception.NotFoundException;
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
import utils.SensorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static play.libs.Json.toJson;

@With(SecurityCtrl.class)
public class SensorCtrl extends Controller {
    //GET
    public static Result getAllSensors() {
        List<Sensor> sensors;
        try {
            sensors = SensorUtils.getAllSensors(SecurityCtrl.getUser());
        } catch (NoUserException e) {
            return badRequest(e.getMessage());
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
        JsonNode json = request().body().asJson();
        if(json == null) return badRequest("Expecting Json data");
        Sensor sensor;
        try {
            sensor = SensorUtils.createSensor(SecurityCtrl.getUser(), json.findPath("label").asText(), json.findPath("description").asText());
        } catch(NoUserException e) {
            return badRequest(e.getMessage());
        }
        return ok(toJson(sensor)).as("application/json");
    }

    //POST
    public static Result updateSensor(String sensorId) {
        JsonNode json = request().body().asJson();
        if(json == null) return badRequest("Expecting Json data");
        Sensor sensor;
        try {
            sensor = SensorUtils.updateSensor(  SecurityCtrl.getUser(),
                                                sensorId,
                                                json.findPath("label").asText(),
                                                json.findPath("description").asText());
        } catch(NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok(toJson(sensor)).as("application/json");
    }

    public static Result deleteSensor(String sensorId) {
        try {
            SensorUtils.deleteSensor(SecurityCtrl.getUser(), sensorId);
        } catch(NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok().as("application/json");
    }

    //POST
    public static Result generateNewKey(String sensorId) {
        Sensor sensor;
        try {
            sensor = SensorUtils.generateNewKey(SecurityCtrl.getUser(), sensorId);
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        ObjectNode result = Json.newObject();
        result.put("sensorId", sensor.getSensorId());
        return ok(result).as("application/json");
    }

}
