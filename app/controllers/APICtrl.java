package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import engine.SceneEngine;
import models.Data;
import models.Feed;
import models.User;
import org.drools.runtime.rule.QueryResultsRow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import query.QueryHelper;

import java.util.List;

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
