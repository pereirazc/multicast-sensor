package models;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static play.libs.Json.toJson;

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

    public ObjectNode asJson() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("device", toJson(device));
        node.put("notification", notification.getId());
        return node;
    }

}
