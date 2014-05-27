package models;

import java.io.Serializable;

/**
 * Created by pereirazc on 11/05/14.
 */
public class Device implements Serializable {

    private String token;
    private String os;
    private User owner;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}
