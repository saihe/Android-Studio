package jp.co.july.tokyo.weatherforcassts;

import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import org.json.JSONException;


/**
 * Created by saito.kyohei on 2015/09/16.
 */
public class GetWeatherForecastApiTask extends AsyncTask<String, Void, WeatherForecast>{
    private final Context context;
    Exception exception;

    public GetWeatherForecastApiTask(Context context){
        this.context = context;
    }

    @Override
    protected WeatherForecast doInBackground(String... params){
        try{
            return WeatherApi.getWeather(params[0]);
        }catch (IOException e){
            exception= e;
        }catch(JSONException e){
            exception = e;
        }
        return null;
    }
}
