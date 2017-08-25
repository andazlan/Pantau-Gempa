package com.andazlan.pantaugempa;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andazlan on 8/9/17.
 */

public class Earthquake {
    private static final String LOCATION_SEPARATOR = "of";
    private double mMagnitude;
    private String mLocation;
    private long mTime;
    private String mDistance;
    private String mLocationName;
    private String mUrl;

    public Earthquake(double magnitude, String location, long time) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mTime = time;
        this.mUrl = "";
        this.createLabelPlace();
    }

    public Earthquake(JSONObject properties) {
        try {
            this.mMagnitude = properties.getDouble("mag");
            this.mLocation = properties.getString("place");
            this.mTime = properties.getLong("time");
            this.mUrl = properties.getString("url");
            this.createLabelPlace();
            //this.mDate =
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public void setMagnitude(double magnitude) {
        this.mMagnitude = mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = mLocation;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        this.mDistance = distance;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String locationName) {
        this.mLocationName = locationName;
    }

    public String getDate(){
        Date dateObject = new Date(getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String dateToDisplay = dateFormat.format(dateObject);
        return dateToDisplay;
    }

    public String getHour(){
        Date dateObject = new Date(getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String dateToDisplay = dateFormat.format(dateObject);
        return dateToDisplay;
    }

    public void createLabelPlace(){
        if (this.getLocation().contains(LOCATION_SEPARATOR)) {
            String[] parts = this.getLocation().split(LOCATION_SEPARATOR);
            this.setDistance(parts[0] + " " + LOCATION_SEPARATOR);
            this.setLocationName(parts[1].trim());
        }
        else{
            this.setDistance("Near the");
            setLocationName(this.getLocation());
        }
    }

    public String getLabelMagnitude(){
        DecimalFormat format = new DecimalFormat("0.0");
        String output = format.format(this.getMagnitude());
        return output;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
