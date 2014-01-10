package query;

import models.*;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryHelper {

    public static QueryResults getAllSensors(StatefulKnowledgeSession ksession) {
        return ksession.getQueryResults("Sensors");
    }

    public static QueryResults getAllFeeds(StatefulKnowledgeSession ksession, String key) {
        return ksession.getQueryResults("Feeds", new Object[] {  key });
    }

    public static QueryResultsRow getSensor(StatefulKnowledgeSession ksession, String key) {
        QueryResults results = ksession.getQueryResults("Sensor", new Object[] {  key } );
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) return i.next();
        else return null;
    }

    public static QueryResultsRow getFeed(StatefulKnowledgeSession ksession, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("Feed", new Object[] {  sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) return i.next();
        else return null;
    }


    public static List<Data> GetFeedStream(StatefulKnowledgeSession ksession, String sensorId, String feedId) {
        QueryResults results = ksession.getQueryResults("FeedStream", new Object[] {  sensorId, feedId });
        Iterator<QueryResultsRow> i = results.iterator();
        if (i.hasNext()) {
            return (List<Data>) i.next().get("stream");
        }
        else return null;
    }
}
