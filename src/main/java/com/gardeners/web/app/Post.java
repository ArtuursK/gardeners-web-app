package com.gardeners.web.app;

import java.sql.Timestamp;

public class Post {

    int post_id;
    int gardener_id;
    String description;
    Timestamp datetime;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getGardener_id() {
        return gardener_id;
    }

    public void setGardener_id(int gardener_id) {
        this.gardener_id = gardener_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}
