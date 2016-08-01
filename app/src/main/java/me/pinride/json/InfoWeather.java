package me.pinride.json;

import java.io.Serializable;

/**
 * Created by Lê Quý Hiếu on 22/07/2016.
 */
public class InfoWeather implements Serializable{
    private String date;
    private String tempC;
    private String condition;
    String linkCondition;

    public InfoWeather(String date, String tempC, String condition, String linkCondition) {
        this.date = date;
        this.tempC = tempC;
        this.condition = condition;
        this.linkCondition = linkCondition;
    }

    public InfoWeather() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getlinkCondition() {
        return linkCondition;
    }

    public void setLinkCondition(String linkCondition) {
        this.linkCondition = linkCondition;
    }
}
