package models;

import java.io.Serializable;

/**
 * Created by pereirazc on 23/05/14.
 */
public class AlertConfiguration implements Serializable {

    private double min;
    private double max;

    public AlertConfiguration() {
        reset();
    }

    public void reset() {
        min = Double.MIN_VALUE;
        max = Double.MAX_VALUE;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

}
