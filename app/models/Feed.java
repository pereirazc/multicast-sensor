package models;

public class Feed {

    private String feedId;
    //private String label;
    private String description;
    private Sensor sensor;

    public Feed(Sensor s) {
        sensor = s;
        sensor.addFeed(this);
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    //public String getLabel() {
    //    return label;
    //}

    //public void setLabel(String label) {
    //    this.label = label;
    //}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
