package com.example.keepair.myapplication.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Keepair on 2016-09-17.
 */
public class Flower implements Serializable {


    @Expose
    private String author;

    @Expose
    private String text;

    @Expose
    private String image;

    @Expose
    private Point point;

    @Expose
    private String created_date;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public Flower(String author, String text, String image, Point point, String created_date) {

        this.author = author;
        this.text = text;
        this.image = image;
        this.point = point;
        this.created_date = created_date;
    }

    public class Point implements Serializable {
        @Expose
        private double longitude;
        @Expose
        private double latitude;
        public Point(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
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
    }


}
