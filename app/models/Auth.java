package models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Isaac
 * Date: 14/01/14
 * Time: 04:30
 * To change this template use File | Settings | File Templates.
 */
public class Auth implements Serializable {

    private String token;
    private User user;

    public Auth(User user) {
        setUser(user);
        setToken(UUID.randomUUID().toString());
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
