package models;

import java.io.Serializable;

/**
 * Created by pereirazc on 11/05/14.
 */
public class Device implements Serializable {

    private String deviceId;
    private String os;
    private User owner;

    public Device(User user, String deviceId) {
        this.owner = user;
        this.deviceId = deviceId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
