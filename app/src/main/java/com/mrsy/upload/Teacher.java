package com.mrsy.upload;

import com.google.firebase.database.Exclude;
public class Teacher {
    private String title;
    private String URL;
    private String key;
    private String description;
    private int position;

    public Teacher() {
        //empty constructor needed
    }
    public Teacher (int position){
        this.position = position;
    }
    public Teacher(String title, String url ,String Des) {
        if (title.trim().equals("")) {
            title = "No Title";
        }
        this.title = title;
        this.URL = url;
        this.description = Des;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getURL() {
        return URL;
    }
    public void setURL(String url) {
        this.URL = url;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
