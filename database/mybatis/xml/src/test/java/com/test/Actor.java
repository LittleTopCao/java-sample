package com.test;

import java.sql.Timestamp;

/**
 * 演员
 */
public class Actor {
    public Short actorId;
    public String firstName;
    public String lastName;
    public Timestamp lastUpdate;

    public Actor() {
    }

    public Actor(String firstName, String lastName, Timestamp lastUpdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
    }


    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
