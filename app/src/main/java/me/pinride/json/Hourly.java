package me.pinride.json;

import java.util.ArrayList;

/**
 * Created by Lê Quý Hiếu on 20/07/2016.
 */
public class Hourly {


    private ArrayList<WeatherDesc> weatherDesc = new ArrayList<WeatherDesc>();
    private ArrayList<WeatherIconUrl> weatherIconUrl = new ArrayList<WeatherIconUrl>();

    public ArrayList<WeatherIconUrl> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(ArrayList<WeatherIconUrl> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public ArrayList<WeatherDesc> getWeatherDescs() {
        return weatherDesc;
    }

    public void setWeatherDescs(ArrayList<WeatherDesc> weatherDescs) {
        this.weatherDesc = weatherDescs;
    }
}

