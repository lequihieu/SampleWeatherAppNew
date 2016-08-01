package me.pinride.writedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.pinride.json.InfoWeather;
import me.pinride.json.WeatherData;
import me.pinride.model.Weather;

/**
 * Created by Lê Quý Hiếu on 26/07/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "weather.sqlite";
    public static final String TABLE_NAME = "InfoWeather";
    public static final String COL_DATE = "Date";
    public static final String COL_TEMP = "Temp";
    public static final String COL_CONDITION = "Condition";
    public static final String COL_LINK = "linkCondition";
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public boolean insertData(WeatherData weatherData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String currentDay = weatherData.getData().getCurrent_condition().get(0).getLocalObsDateTime();
        ArrayList<Weather> weather = weatherData.getData().getWeather();

        contentValues.put("DatePrevious", currentDay);
        for(int i = 0; i < 4; i++) {
            int j = i + 1;

            Weather element = weather.get(i);
            String date = element.getDate();
            String tempC = "MIN " + element.getMintempC() + " " + "MAX " + element.getMaxtempC();
            String condition = element.getHourly().get(0).getWeatherDescs().get(0).getValue();
            String linkUrl = element.getHourly().get(0).getWeatherIconUrl().get(0).getValue();

            contentValues.put(COL_DATE+j, date);
            contentValues.put(COL_TEMP+j, tempC);
            contentValues.put(COL_CONDITION+j, condition);
            contentValues.put(COL_LINK+j, linkUrl);

        }

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}
