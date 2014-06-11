package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import engine.SceneEngine;
import models.AlertConfiguration;
import models.Feed;
import models.Sensor;
import models.User;
import org.drools.runtime.rule.QueryResultsRow;
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
                Feed feed = new Feed();
                feed.setSensor(sensor);
                if (json.has("feedId"))          feed.setFeedId(json.findPath("feedId").asText());
                if (json.has("description"))    feed.setDescription(json.findPath("description").asText());

                JsonNode alertNode = json.findPath("alertConfig");
                if (!alertNode.isMissingNode() && alertNode.findPath("status").asBoolean()) {
                    AlertConfiguration alertConf = new AlertConfiguration();
                    feed.setAlertConfig(alertConf);
                    if (!alertNode.findPath("min").isMissingNode()) alertConf.setMin(alertNode.findPath("min").asDouble());
                    if (!alertNode.findPath("max").isMissingNode()) alertConf.setMax(alertNode.findPath("max").asDouble());
                    SceneEngine.getInstance().getSession().insert(alertConf);
                }
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
                AlertConfiguration alertConf = feed.getAlertConfig();
                if (json.has("feedId"))          feed.setFeedId(json.findPath("feedId").asText());
                if (json.has("description"))    feed.setDescription(json.findPath("description").asText());
                JsonNode alertNode = json.findPath("alertConfig");
                if (!alertNode.isMissingNode() && alertNode.findPath("status").asBoolean()) {
                    if (alertConf == null) {
                        alertConf = new AlertConfiguration();
                        feed.setAlertConfig(alertConf);
                        if (!alertNode.findPath("min").isMissingNode())
                            alertConf.setMin(alertNode.findPath("min").asDouble());
                        if (!alertNode.findPath("max").isMissingNode())
                            alertConf.setMax(alertNode.findPath("max").asDouble());
                        SceneEngine.getInstance().getSession().insert(alertConf);
                    } else {
                        alertConf.reset();
                        if (!alertNode.findPath("min").isMissingNode())
                            alertConf.setMin(alertNode.findPath("min").asDouble());
                        if (!alertNode.findPath("max").isMissingNode())
                            alertConf.setMax(alertNode.findPath("max").asDouble());
                        SceneEngine.getInstance().getSession().update(SceneEngine.getInstance().getSession().getFactHandle(alertConf), alertConf);
                    }
                } else {
                    if (alertConf != null) {
                        feed.setAlertConfig(null);
                        SceneEngine.getInstance().getSession().retract(SceneEngine.getInstance().getSession().getFactHandle(alertConf));
                    }
                }
                SceneEngine.getInstance().getSession().update(r.getFactHandle("feed"), feed);
                JsonNode result = toJson(feed);
                return ok(result).as("application/json");
            } else return badRequest("Couldn't find Feed");
        } else return badRequest("Expecting Json data");
    }

    //DELETE
    public static Result deleteFeed(String sensorId, String feedId) {
        User user = SecurityCtrl.getUser();
        QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (r != null) {
            Feed feed = (Feed) r.get("feed");
            AlertConfiguration alertConfig = feed.getAlertConfig();
            if (alertConfig != null) {
                SceneEngine.getInstance().getSession().retract(SceneEngine.getInstance().getSession().getFactHandle(alertConfig));
            }
            SceneEngine.getInstance().getSession().retract(r.getFactHandle("feed"));
            return ok().as("application/json");
        } else return badRequest("Couldn't find Feed");
    }

}
