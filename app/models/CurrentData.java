package models;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;

/**
 * Created by pereirazc on 25/05/14.
 */
public class CurrentData implements Serializable {

    private Feed feed;
    private long timestamp;
    private double value;

    public CurrentData(Feed feed) {
        this.feed = feed;
    }

    public void updateData(Data data) {
        this.timestamp = data.getTimestamp();
        this.value = data.getValue();
    }

    public long getTimestamp() {
        return timestamp;
    }
    public double getValue() {
        return value;
    }
    public Feed getFeed() {
        return feed;
    }

}
