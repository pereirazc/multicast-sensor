package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Isaac
 * Date: 04/01/14
 * Time: 02:28
 * To change this template use File | Settings | File Templates.
 */

public class Feed implements Serializable {
    private String feedId;
    //private String label;
    private String description;
    private Sensor sensor;
    private AlertConfiguration alertConfig;

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    //public String getLabel() {

    //public void setLabel(String label) {

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

    //}
    //    this.label = label;
    //}
    //    return label;
    public AlertConfiguration getAlertConfig() {
        return alertConfig;
    }

    public void setAlertConfig(AlertConfiguration alertConfig) {
        this.alertConfig = alertConfig;
    }
}
