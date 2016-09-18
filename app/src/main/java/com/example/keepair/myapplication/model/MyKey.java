package com.example.keepair.myapplication.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Keepair on 2016-09-17.
 */
public class MyKey {

    @Expose
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MyKey(String key) {

        this.key = key;
    }
}
