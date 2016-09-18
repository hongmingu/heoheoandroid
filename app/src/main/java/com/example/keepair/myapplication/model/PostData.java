package com.example.keepair.myapplication.model;

import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Keepair on 2016-09-18.
 */
public class PostData implements Serializable {

    @Expose
    private String text;

    @Expose
    private Point point;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public PostData(String text, Point point) {


        this.text = text;
        this.point = point;
    }
}


