package me.pinride.anothermain;

import android.content.ContentValues;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import me.pinride.adapter.WeatherAdapter;
import me.pinride.json.InfoWeather;
import me.pinride.model.Weather;
import me.pinride.json.WeatherData;
import me.pinride.sampleweatherapp.MainActivity;
import me.pinride.sampleweatherapp.R;
import me.pinride.writedatabase.DatabaseHelper;

public class GetDataAndShowListDay extends AppCompatActivity {
    Button btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_get_and_show);
        /**weatherDataTask for saving info weather to database and drawing list Weather **/
        WeatherDataTask weatherDataTask = new WeatherDataTask();
        weatherDataTask.execute();
        /**addControls to return MainActivity**/
        addControls();
    }

    private void addControls() {
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnBtn = new Intent(GetDataAndShowListDay.this, MainActivity.class);
                startActivity(returnBtn);
            }
        });
    }


    class WeatherDataTask extends AsyncTask<Void, Void, WeatherData>{
        @Override
        protected void onPostExecute(WeatherData weatherData) {
            super.onPostExecute(weatherData);
            /**Saving Database**/

            ArrayList<Weather> weather = weatherData.getData().getWeather();
            final ArrayList<InfoWeather> listInfoWeathers = new ArrayList<>();
            DatabaseHelper db = new DatabaseHelper(GetDataAndShowListDay.this,"weather.sqlite",null,1);
            Boolean checkInsert = db.insertData(weatherData);

            if(checkInsert) Toast.makeText(GetDataAndShowListDay.this,"Insert Succesful",Toast.LENGTH_LONG).show();
            else Toast.makeText(GetDataAndShowListDay.this,"Insert Failed",Toast.LENGTH_LONG).show();
            db.close();

            for(int i = 0; i < 4; i++) {
                int j = i + 1;
                InfoWeather elementInfo = new InfoWeather();
                Weather element = weather.get(i);
                String date = element.getDate();
                String tempC = "MIN " + element.getMintempC() + " " + "MAX " + element.getMaxtempC();
                String condition = element.getHourly().get(0).getWeatherDescs().get(0).getValue();
                String linkUrl = element.getHourly().get(0).getWeatherIconUrl().get(0).getValue();

                elementInfo.setCondition(condition);
                elementInfo.setLinkCondition(linkUrl);
                elementInfo.setDate(date);
                elementInfo.setTempC(tempC);

                listInfoWeathers.add(elementInfo);
            }

            /**Drawing list weather day**/
            ListView lvInfo = (ListView) findViewById(R.id.lvInfo);
            WeatherAdapter adapterWeather;
            adapterWeather = new WeatherAdapter(
                    GetDataAndShowListDay.this,
                    R.layout.item,
                    listInfoWeathers
            );
            lvInfo.setAdapter(adapterWeather);
            lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(GetDataAndShowListDay.this, ShowTheSelectedDate.class);
                    InfoWeather infoWeather = listInfoWeathers.get(position);
                    intent.putExtra("DATA1", infoWeather);
                    startActivity(intent);
                }
            });
        }
        @Override
        protected WeatherData doInBackground(Void... params) {
            WeatherData outputWeather = new WeatherData();
            try{
                URL url = new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=ae105daf89c74bcf907174549161807&q=16.451,107.565&num_of_days=5&tp=24&format=json&extra=localObsTime");
                InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(),"UTF-8");

                WeatherData weatherData =
                        new Gson().fromJson(inputStreamReader, WeatherData.class);
                return weatherData;

            }catch (Exception ex){
                Log.e("LOI", ex.toString());
            }
            return outputWeather;

        }
    }
}
