package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.SceneEngine;
import exception.NoUserException;
import exception.NotFoundException;
import models.Feed;
import models.Sensor;
import models.User;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import play.data.DynamicForm;
import play.data.Form;
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
    public static Result sensorDashboard() {
        List<Sensor> sensors;
        try {
            sensors = SensorUtils.getAllSensors(SecurityCtrl.getUser());
        } catch (NoUserException e) {
            return badRequest(e.getMessage());
        }
        return ok(views.html.dashboard.index.render(sensors));
    }

    //GET
    public static Result newSensor() {
        return ok(views.html.sensor.sensor_modal.render(null));
    }

    //GET
    public static Result editSensor(String sensorId) {

        Sensor sensor;
        try {
            sensor = SensorUtils.getSensor(SecurityCtrl.getUser(), sensorId);
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok(views.html.sensor.sensor_modal.render(sensor));
    }

    //GET
    public static Result getSensor(String sensorId) {

        Sensor sensor;
        try {
            sensor = SensorUtils.getSensor(SecurityCtrl.getUser(), sensorId);
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return ok(views.html.sensor.index.render(sensor));
    }

    //POST
    public static Result createSensor() {

        DynamicForm editForm = Form.form().bindFromRequest();
        String label = editForm.get("label");
        String description = editForm.get("description");
        Sensor sensor;
        try {
            sensor = SensorUtils.createSensor(SecurityCtrl.getUser(), label, description);
        } catch(NoUserException e) {
            return badRequest(e.getMessage());
        }

        return redirect(routes.SensorCtrl.getSensor(sensor.getSensorId()));
    }

    //POST
    public static Result updateSensor(String sensorId) {

        DynamicForm editForm = Form.form().bindFromRequest();
        String label = editForm.get("label");
        String description = editForm.get("description");
        Sensor sensor;
        try {
            sensor = SensorUtils.updateSensor(SecurityCtrl.getUser(), sensorId, label, description);
        } catch(NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return redirect(routes.SensorCtrl.getSensor(sensor.getSensorId()));
    }

    public static Result deleteSensor(String sensorId) {
        try {
            SensorUtils.deleteSensor(SecurityCtrl.getUser(), sensorId);
        } catch (NoUserException | NotFoundException e) {
            return badRequest(e.getMessage());
        }
        return redirect(routes.SensorCtrl.sensorDashboard());
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
