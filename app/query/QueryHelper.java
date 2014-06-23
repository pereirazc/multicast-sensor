package query;

import models.Data;
import models.Delivered;
import models.Device;
import models.User;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryHelper {

    public static User getUserByMailAndPassword(StatefulKnowledgeSession ksession, String mail, String pswd) {
        QueryResults results = ksession.getQueryResults("UserByMailAndPassword", new Object[] {  mail, pswd } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            QueryResultsRow r = i.next();
            return (User) r.get("user");
        }
        else return null;
    }

    public static User getUserByMail(StatefulKnowledgeSession ksession, String mail) {
        QueryResults results = ksession.getQueryResults("UserByMail", new Object[] {  mail } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            QueryResultsRow r = i.next();
            return (User) r.get("user");
        }
        else return null;
    }

    public static QueryResultsRow getUserByToken(StatefulKnowledgeSession ksession, String token) {
        QueryResults results = ksession.getQueryResults("UserByToken", new Object[] {  token } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return i.next();
        }
        else return null;
    }

    public static QueryResultsRow getDevice(StatefulKnowledgeSession ksession, long userId, String deviceId) {
        QueryResults results = ksession.getQueryResults("DeviceById", new Object[] {  userId, deviceId } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return i.next();
        }
        else return null;
    }

    public static List<Delivered> getDeliveredNotifications(StatefulKnowledgeSession ksession) {
        List<Delivered> d = new ArrayList<Delivered>();
        QueryResults results = ksession.getQueryResults("DeliveredNotifications", new Object[] { } );
        for(QueryResultsRow r: results) {
            d.add( (Delivered) r.get("delivered"));
        }
        return d;
    }

    public static QueryResultsRow getNotification(StatefulKnowledgeSession ksession, long userId, long notificationId) {
        QueryResults results = ksession.getQueryResults("NotificationById", new Object[] {  userId, notificationId } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return i.next();
        }
        else return null;
    }

    public static QueryResults getNotifications(StatefulKnowledgeSession ksession, long userId, String deviceId) {
        return ksession.getQueryResults("NotificationsByUser", new Object[] {userId, deviceId});
    }

    public static QueryResults getAllNotifications(StatefulKnowledgeSession ksession, long min) {
        return ksession.getQueryResults("AllNotifications", new Object[] {min});
    }

    public static QueryResults getAllSensors(StatefulKnowledgeSession ksession, long ownerId) {
        return ksession.getQueryResults("Sensors", new Object[] {  ownerId });
    }

    public static QueryResults getAllFeeds(StatefulKnowledgeSession ksession, long ownerId, String sensorId) {
        return ksession.getQueryResults("Feeds", new Object[] {ownerId, sensorId });
    }

    public static QueryResultsRow getSensor(StatefulKnowledgeSession ksession, long ownerId, String sensorId) {
        QueryResults results = ksession.getQueryResults("Sensor", new Object[] {  ownerId, sensorId } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) return i.next();
        else return null;
    }

    public static QueryResultsRow getFeed(StatefulKnowledgeSession ksession, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("Feed", new Object[] {sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) return i.next();
        else return null;
    }

    public static QueryResultsRow getFeedByUser(StatefulKnowledgeSession ksession, long ownerId, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("FeedByUser", new Object[] {ownerId,  sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) return i.next();
        else return null;
    }

    public static List<Data> GetFeedStream(StatefulKnowledgeSession ksession, long ownerId, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("FeedStream", new Object[] { ownerId, sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return (List<Data>) i.next().get("stream");
        }
        else return null;
    }

    public static List<Device> getRegisteredDevices(StatefulKnowledgeSession ksession) {

        ArrayList<Device> list = new ArrayList<>();

        QueryResults results = ksession.getQueryResults("Devices", new Object[] {  });

        for (QueryResultsRow r: results) {
            list.add( (Device) r.get("device") );
        }

        return list;
    }

}
