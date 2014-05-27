package models;

import java.io.Serializable;

/**
 * Created by pereirazc on 24/05/14.
 */
public class Notification implements Serializable {

    private User user;
    private long timestamp;
    private String message;

    public Notification(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messsage) {
        this.message = messsage;
    }

}
