package me.pinride.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import me.pinride.json.InfoWeather;
import me.pinride.sampleweatherapp.R;

/**
 * Created by Lê Quý Hiếu on 22/07/2016.
 */

public class WeatherAdapter extends ArrayAdapter<InfoWeather>{
    Activity context;
    int resource;
    List<InfoWeather> objects;

    public WeatherAdapter(Activity context, int resource, List<InfoWeather> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row  = layoutInflater.inflate(this.resource, null);
        TextView txtDay = (TextView) row.findViewById(R.id.txtDay);
        TextView txtTemp = (TextView) row.findViewById(R.id.txtTemp);
        final ImageView imgCondition = (ImageView) row.findViewById(R.id.imgCondition);

        final InfoWeather infoWeather = this.objects.get(position);

        txtDay.setText(infoWeather.getDate());
        txtTemp.setText(infoWeather.getTempC());
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
                    Log.e("LOI", ex.toString());
                }
                return null;
            }
        }
        ImageTask task = new ImageTask();
        task.execute(infoWeather.getlinkCondition());

        return row;
    }
}
