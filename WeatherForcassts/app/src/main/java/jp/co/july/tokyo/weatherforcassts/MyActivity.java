package jp.co.july.tokyo.weatherforcassts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;

/*
import android.os.Handler;
import java.io.IOException;
*/

public class MyActivity extends AppCompatActivity {
    //private TextView textView;
    private TextView location;
    private LinearLayout forecastLayout;
    private ProgressBar progress;

    private class GetWeatherForecastTask extends GetWeatherForecastApiTask{
        public  GetWeatherForecastTask(Context context){
            super(context);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(WeatherForecast data){
            super.onPostExecute(data);
            progress.setVisibility(View.GONE);
            if(data != null){
                //textView.setText(data);
                //textView.setText(data.location.area + " " + data.location.prefecture + " " + data.location.city);
                location.setText(data.location.area + " " + data.location.prefecture + " " + data.location.city);

                //予報を一覧表示
                for(WeatherForecast.Forecast forecast : data.forecastList){
                    //textView.append("\n");
                    //textView.append(forecast.dateLabel + " " + forecast.telop);
                    View row = View.inflate(MyActivity.this, R.layout.forecasts_row, null);

                    TextView date = (TextView) row.findViewById(R.id.tv_date);
                    date.setText(forecast.dateLabel);

                    TextView telop = (TextView) row.findViewById(R.id.tv_telop);
                    telop.setText(forecast.telop);

                    TextView temp = (TextView) row.findViewById(R.id.tv_tempreture);
                    temp.setText(forecast.temperature.toString());

                    ImageView imageView = (ImageView) row.findViewById(R.id.tv_weather);
                    imageView.setTag(forecast.image.url);//ImageLoaderTask.java.getTag()の返し

                    ImageLoaderTask task = new ImageLoaderTask(MyActivity.this);
                    task.execute(imageView);

                    forecastLayout.addView(row);
                }
            }else if(exception != null){
                Toast.makeText(MyActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    //private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //handler = new Handler();
        setContentView(R.layout.activity_my);
        //textView = (TextView) findViewById(R.id.tv_main);
        location = (TextView) findViewById(R.id.tv_location);
        forecastLayout = (LinearLayout) findViewById(R.id.ll_forecasts);
        progress = (ProgressBar) findViewById(R.id.progress);

        /*
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    final String data = WeatherApi.getWeather("400040");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(data);
                        }
                    });
                }catch (final IOException e){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        thread.start();
        */
        new GetWeatherForecastTask(this).execute("400040");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
