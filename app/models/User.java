package models;

import java.io.Serializable;

public class User implements Serializable {

    private static long last_id = 0;

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User() {
        last_id++;
        this.userId = last_id;
    }

    //private String username;
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
