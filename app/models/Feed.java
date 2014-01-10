package models;

/**
 * Created with IntelliJ IDEA.
 * User: Isaac
 * Date: 04/01/14
 * Time: 02:28
 * To change this template use File | Settings | File Templates.
 */
public class Feed {

    private String feedId;
    //private String label;
    private String description;
    private Sensor sensor;

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
