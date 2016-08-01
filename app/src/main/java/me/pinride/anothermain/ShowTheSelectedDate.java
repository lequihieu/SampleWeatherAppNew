package me.pinride.anothermain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import me.pinride.json.InfoWeather;
import me.pinride.sampleweatherapp.R;

public class ShowTheSelectedDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_the_selected_date);
        addControls();
    }

    private void addControls() {
        TextView txtTempC = (TextView) findViewById(R.id.txtTempC);
        TextView txtCondition = (TextView) findViewById(R.id.txtCondition);
        final ImageView imgCondition = (ImageView) findViewById(R.id.imgCondition);

        Intent intent = getIntent();
        InfoWeather infoWeather = (InfoWeather) intent.getSerializableExtra("DATA1");
        txtTempC.setText(infoWeather.getTempC());
        txtCondition.setText(infoWeather.getCondition());

        class ImageTask extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imgCondition.setImageBitmap(bitmap);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap = null;

                try {
                    String link = params[0];
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                    return bitmap;
                } catch (Exception ex) {
                    Log.e("LOI12", ex.toString());
                }
                return null;
            }
        }
        ImageTask task = new ImageTask();
        task.execute(infoWeather.getlinkCondition());

    }
}
