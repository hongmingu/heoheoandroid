package com.example.keepair.myapplication.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Point implements Serializable {
    @Expose
    private double longitude;
    @Expose
    private double latitude;

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
