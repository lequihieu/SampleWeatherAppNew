package me.pinride.anothermain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import me.pinride.adapter.WeatherAdapter;
import me.pinride.json.InfoWeather;
import me.pinride.sampleweatherapp.R;

public class ShowListDayWeather extends AppCompatActivity {
    ListView lvInfo;
    ArrayList<InfoWeather> listWeather;
    WeatherAdapter adapterWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_day_weather);
        addControls();
    }

    private void addControls() {
        final Intent intent = getIntent();
        listWeather = (ArrayList<InfoWeather>) intent.getSerializableExtra("DATA");
        lvInfo = (ListView) findViewById(R.id.lvInfo);
        adapterWeather = new WeatherAdapter(
                ShowListDayWeather.this,
                R.layout.item,
                listWeather
        );
        lvInfo.setAdapter(adapterWeather);
        lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowListDayWeather.this, ShowTheSelectedDate.class);
                InfoWeather infoWeather = listWeather.get(position);
                intent.putExtra("DATA1", infoWeather);
                startActivity(intent);
            }
        });

    }
}
