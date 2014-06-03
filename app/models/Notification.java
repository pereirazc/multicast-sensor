package models;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;

/**
 * Created by pereirazc on 24/05/14.
 */
public class Notification implements Serializable {

    private static long last_id = 0;

    private long id;
    private long timestamp;
    private long duration;
    private String type;
    private Data data;
    private AlertConfiguration configuration;

    public Notification(long timestamp, String type, CurrentData current) {
        last_id++;
        this.id = last_id;
        this.timestamp = timestamp;
        this.type = type;
        this.data = new Data();
        this.data.setFeed(current.getFeed());
        this.data.setTimestamp(current.getTimestamp());
        this.data.setValue(current.getValue());

        this.configuration = new AlertConfiguration();
        this.configuration.setMin(current.getFeed().getAlertConfig().getMin());
        this.configuration.setMax(current.getFeed().getAlertConfig().getMax());
    }

    public Notification(long timestamp, String type, CurrentData current, long duration) {
        last_id++;
        this.id = last_id;
        this.timestamp = timestamp;
        this.type = type;

        this.duration = duration;

        this.data = new Data();
        this.data.setFeed(current.getFeed());
        this.data.setTimestamp(current.getTimestamp());
        this.data.setValue(current.getValue());

        this.configuration = new AlertConfiguration();
        this.configuration.setMin(current.getFeed().getAlertConfig().getMin());
        this.configuration.setMax(current.getFeed().getAlertConfig().getMax());
    }

    public long getId() {
        return this.id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ObjectNode toJson() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("notificationId", id);
        node.put("timestamp", timestamp);
        node.put("type", type);
        if (type.equals("FINISH")) {
            node.put("duration", duration);
        } else node.put("duration", "");
        node.put("sensoId", data.getFeed().getSensor().getSensorId());
        node.put("feedId", data.getFeed().getFeedId());
        node.put("configuration", play.libs.Json.toJson(configuration));
        node.put("data", data.toJson());
        return node;
    }

}
