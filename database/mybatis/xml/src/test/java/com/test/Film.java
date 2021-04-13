package com.test;

import java.sql.Timestamp;

/**
 * 电影
 */
public class Film {
    public Short film_id;
    public String title;
    public String description;

    public Film() {
    }

    public Film(Short film_id, String title, String description) {
        this.film_id = film_id;
        this.title = title;
        this.description = description;
    }
}