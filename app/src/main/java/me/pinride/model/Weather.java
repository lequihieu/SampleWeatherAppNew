package me.pinride.model;

import java.util.ArrayList;

import me.pinride.json.Hourly;

/**
 * Created by Lê Quý Hiếu on 20/07/2016.
 */
public class Weather {

    private ArrayList<Hourly> hourly;
    private String date;
    private String maxtempC;
    private String mintempC;

    public ArrayList<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(ArrayList<Hourly> hourly) {
        this.hourly = hourly;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public Weather(ArrayList<Hourly> hourly, String date, String maxtempC, String mintempC) {
        this.hourly = hourly;
        this.date = date;
        this.maxtempC = maxtempC;
        this.mintempC = mintempC;
    }

    public Weather() {
    }
}
