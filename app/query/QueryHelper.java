package query;

import models.Data;
import models.User;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

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

    public static QueryResultsRow getUserByToken(StatefulKnowledgeSession ksession, String token) {
        QueryResults results = ksession.getQueryResults("UserByToken", new Object[] {  token } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return i.next();
        }
        else return null;
    }

    public static QueryResults getNotifications(StatefulKnowledgeSession ksession, String userId) {
        return ksession.getQueryResults("NotificationsByUser", new Object[] {userId});
    }

    public static QueryResults getAllNotifications(StatefulKnowledgeSession ksession) {
        return ksession.getQueryResults("AllNotifications", new Object[] {});
    }

    public static QueryResults getAllSensors(StatefulKnowledgeSession ksession, String ownerId) {
        return ksession.getQueryResults("Sensors", new Object[] {  ownerId });
    }

    public static QueryResults getAllFeeds(StatefulKnowledgeSession ksession, String ownerId, String sensorId) {
        return ksession.getQueryResults("Feeds", new Object[] {ownerId, sensorId });
    }

    public static QueryResultsRow getSensor(StatefulKnowledgeSession ksession, String ownerId, String sensorId) {
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

    public static QueryResultsRow getFeedByUser(StatefulKnowledgeSession ksession, String ownerId, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("FeedByUser", new Object[] {ownerId,  sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) return i.next();
        else return null;
    }


    public static List<Data> GetFeedStream(StatefulKnowledgeSession ksession, String ownerId, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("FeedStream", new Object[] { ownerId, sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return (List<Data>) i.next().get("stream");
        }
        else return null;
    }
}
