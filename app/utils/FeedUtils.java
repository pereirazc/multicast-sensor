package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import controllers.SecurityCtrl;
import engine.SceneEngine;
import exception.NoUserException;
import exception.NotFoundException;
import models.Feed;
import models.Sensor;
import models.User;
import org.drools.runtime.rule.QueryResultsRow;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import play.libs.Json;
import play.mvc.Result;
import query.QueryHelper;

import static play.libs.Json.toJson;

public class FeedUtils {

    public static Feed getFeed(User user, String sensorId, String feedId) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (r == null) throw new NotFoundException("feed not found");
        Feed feed = (Feed) r.get("feed");
        return feed;
    }

    public static Feed createFeed(User user, String sensorId, String feedId, String description) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getSensor(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId);
        if (r == null) throw new NotFoundException("feed not found");
        Sensor sensor = (Sensor) r.get("sensor");
        Feed feed = new Feed(sensor);
        if (feedId != null)         feed.setFeedId(feedId);
        if (description != null)    feed.setDescription(description);
        SceneEngine.getInstance().getSession().insert(feed);
        return feed;
    }

    public static Feed updateFeed(User user, String sensorId, String feedId, String description) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (r == null) throw new NotFoundException("feed not found");
        Feed feed = (Feed) r.get("feed");
        if (feedId != null)         feed.setFeedId(feedId);
        if (description != null)    feed.setDescription(description);
        SceneEngine.getInstance().getSession().update(r.getFactHandle("feed"), feed);
        return feed;
    }

    public static void deleteFeed(User user, String sensorId, String feedId) throws NoUserException, NotFoundException {
        if (user == null) throw new NoUserException();
        QueryResultsRow r = QueryHelper.getFeedByUser(SceneEngine.getInstance().getSession(), user.getUserId(), sensorId, feedId);
        if (r == null) throw new NotFoundException("feed not found");
        Feed feed = (Feed) r.get("feed");
        feed.getSensor().removeFeed(feed);
        SceneEngine.getInstance().getSession().retract(r.getFactHandle("feed"));

    }

}
