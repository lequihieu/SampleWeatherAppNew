package me.pinride.json;

import java.util.ArrayList;

import me.pinride.json.Current_condition;
import me.pinride.model.Weather;

/**
 * Created by Lê Quý Hiếu on 20/07/2016.
 */
public class Data {
    private ArrayList<Weather>weather;
    private ArrayList<Current_condition> current_condition;

    public ArrayList<Current_condition> getCurrent_condition() {
        return current_condition;
    }

    public void setCurrent_condition(ArrayList<Current_condition> current_condition) {
        this.current_condition = current_condition;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }
}
