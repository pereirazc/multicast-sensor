package utils;

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
import play.mvc.Result;
import query.QueryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static play.libs.Json.toJson;

public class SensorUtils {

    public static List<Sensor> getAllSensors(User user) throws NoUserException {

        if (user == null) throw new NoUserException();

        QueryResults results = QueryHelper.getAllSensors(SceneEngine.getInstance().getSession(), user.getUserId());
        List<Sensor> sensors = new ArrayList<>();
        for (QueryResultsRow r: results) {
            sensors.add((Sensor) r.get("sensor"));
        }
        return sensors;
    }

    public static Sensor getSensor(User user, String sensorId) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r == null) throw new NotFoundException("sensor (" + sensorId + ") not found");
        Sensor sensor = (Sensor) r.get("sensor");
        return sensor;
    }

    public static Sensor createSensor(User user, String label, String description) throws NoUserException {
        if (user == null) throw new NoUserException();
        Sensor sensor = new Sensor(user);
        sensor.setSensorId(UUID.randomUUID().toString());
        if (label != null)          sensor.setLabel(label);
        if (description != null)    sensor.setDescription(description);
        SceneEngine.getInstance().getSession().insert(sensor);
        return sensor;
    }

    public static Sensor updateSensor(User user, String sensorId, String label, String description) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r == null) throw new NotFoundException("sensor (" + sensorId + ") not found");
        Sensor sensor = (Sensor) r.get("sensor");
        if (label != null)          sensor.setLabel(label);
        if (description != null)    sensor.setDescription(description);
        SceneEngine.getInstance().getSession().update(r.getFactHandle("sensor"), sensor);
        return sensor;
    }

    public static void deleteSensor(User user, String sensorId) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r == null) throw new NotFoundException("sensor (" + sensorId + ") not found");
        SceneEngine.getInstance().getSession().retract(r.getFactHandle("sensor"));
    }

    public static Sensor generateNewKey(User user, String sensorId) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r == null) throw new NotFoundException("sensor (" + sensorId + ") not found");
        Sensor sensor = (Sensor) r.get("sensor");
        sensor.setSensorId(UUID.randomUUID().toString());
        SceneEngine.getInstance().getSession().update(r.getFactHandle("sensor"), sensor);
        return sensor;
    }

}
