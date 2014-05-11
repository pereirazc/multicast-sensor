package models;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Sensor {

    private String sensorId;
    private String label;
    private String description;
    private TimeZone timezone;
    private User owner;

    private List<Feed> feeds = new ArrayList<Feed>();

    public Sensor(User owner) {

        this.owner = owner;

    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Feed> getFeeds() {
        return this.feeds;
    }

    public void addFeed(Feed feed) {
        feeds.add(feed);
    }

    public void removeFeed(Feed feed) {
        feeds.remove(feed);
    }

}
