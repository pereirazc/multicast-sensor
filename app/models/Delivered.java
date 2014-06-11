package models;

/**
 * Created by pereirazc on 08/06/14.
 */
public class Delivered {

    private Notification notification;
    private Device device;

    public Delivered(Device device, Notification notification) {
        this.device = device;
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
