package me.pinride.sampleweatherapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import me.pinride.anothermain.GetDataAndShowListDay;
import me.pinride.anothermain.ShowListDayWeather;
import me.pinride.json.InfoWeather;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dsWeatherPrevious = new ArrayList<>();
    ArrayList<ArrayList<InfoWeather>> rootArr = new ArrayList<>();
    ArrayAdapter<String> adapterWeatherPrevious;
    ListView lvPreviousWeather;
    Button btnGetAndShow;

    String DATABASE_NAME="weather.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**copyDatabaseFromAssetToMobile for copying Database from asset to mobile**/
        copyDatabaseFromAssetToMobile();
        /**addControls and showPreviousWeather for Drawing Previous Weather List**/
        addControls();
        showPreviousWeather();
        /**addEvent for Clicking Button 'GET CURRENT HUE WEATHER'**/
        addEvents();
    }

    private void addEvents() {
        btnGetAndShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetDataAndShowListDay.class);
                startActivity(intent);
            }
        });
    }

    private void showPreviousWeather() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query("InfoWeather",null,null,null,null,null,null);
        dsWeatherPrevious.clear();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String datePrevious=cursor.getString(1);
            ArrayList<InfoWeather> arrWeather = new ArrayList<>();
            for(int i = 2; i <= 5; i++) {
                InfoWeather elementWeather = new InfoWeather(
                        cursor.getString(i),
                        cursor.getString(i + 4),
                        cursor.getString(i + 4*2),
                        cursor.getString(  i + 4*3));
                arrWeather.add(elementWeather);
            }
            dsWeatherPrevious.add(datePrevious);
            rootArr.add(arrWeather);
        }
        cursor.close();
        adapterWeatherPrevious.notifyDataSetChanged();
    }

    private void copyDatabaseFromAssetToMobile() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Successfully Retrieve Data", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try
        {
            InputStream myInput=getAssets().open(DATABASE_NAME);
            String outFileName = getLinkDatabase();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists())
            {
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("Loi_SaoChep",ex.toString());
        }
    }

    private String getLinkDatabase() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    private void addControls() {
        lvPreviousWeather = (ListView) findViewById(R.id.lvPreviousWeather);
        btnGetAndShow = (Button) findViewById(R.id.btnGetAndShow);
        dsWeatherPrevious = new ArrayList<>();
        adapterWeatherPrevious = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsWeatherPrevious
        );
        lvPreviousWeather.setAdapter(adapterWeatherPrevious);
        lvPreviousWeather.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowListDayWeather.class);
                ArrayList<InfoWeather> currentLine = rootArr.get(position);
                intent.putExtra("DATA", currentLine);
                startActivity(intent);
            }
        });
    }
}
