package jp.co.july.tokyo.weatherforcassts;

/**
 * Created by saito.kyohei on 2015/09/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class FlagmentWeatherv extends Fragment {

    private static final String KEY_CITY_CODE = "key_city_code";

    public static FragmentWeather newInstance(String cityCode){
        FragmentWeather fragment = new FlagmentWeather();
        Bundle args = new Bundle();
        args.putString(KEY_CITY_CODE, cityCode);
        fragment.setArguments(args);

        return fragment;
    }

    private TextView location;
    private LinearLayout forecastLayout;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my, null);

        location = (TextView) view.findViewById(R.id.tv_location);
        forecastLayout = (LinearLayout) view.findViewById(R.id.ll_forecasts);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        new GetWeatherForecastTask(getActivity()).execute(getArguments().getString(KEY_CITY_CODE));

        return  view;
    }

    private class GetWeatherForecastTask extends GetWeatherForecastApiTask{
        public GetWeatherForecastTask(Context context){
            super(context);
        }

        @Override
        protected void onPostExecute(){
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(WeatherForecast data){
            super.onPreExecute(data);

            if(data != null){
                progress.setVisibility(View.GONE);

                location.setText(data.location.area + " " + " " + data.location.prefecture + " " + data.location.city);

                //予報を一覧表示
                for(WeatherApi.WeatherForecast.Forecast forecast : data.forecastList){
                    View row = View.inflate(getActivity(), R.layout.forecasts_row, null);

                    TextView telop = (TextView) row.findViewById(R.id.tv_telop);
                    telop.setText(forecast.telop);

                    TextView temp = (TextView) row.findViewById(R.id.tv_tempreture);
                    temp.setText(forecast.temperature.toString());

                    ImageView imageView = (ImageView) row.findViewById(R.id.tv_weather);
                    imageView.setTag(forecast.image.url);

                    //読み込み処理の実行
                    ImageLoaderTask task = new ImageLoaderTask(getActivity());
                    task.execute(imageView);

                    forecastLayout.addView(row);
            }
        }else if(exception != null){
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
