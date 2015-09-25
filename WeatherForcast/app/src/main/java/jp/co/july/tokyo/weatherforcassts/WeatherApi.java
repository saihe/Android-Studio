package jp.co.july.tokyo.weatherforcassts;

import android.content.Context;
/*
import android.net.http.AndroidHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
*/
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by saito.kyohei on 2015/09/15.
 */
//Tokyo = 130010
public class WeatherApi {

    private static final String USER_AGENT = "WeatherForecasts Sample";

    private static final String URL =
            "http://weather.livedoor.com/forecast/webservice/json/v1?city=";




    public static WeatherForecast getWeather(String pointId) throws IOException, JSONException {

        // リクエスト送信
        URL requestUrl = new URL(URL + pointId);
        HttpURLConnection connection;
        connection = (HttpURLConnection) requestUrl.openConnection();
        InputStream input = connection.getInputStream();

        // 結果取得

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                input, "UTF-8"));

        String line = reader.readLine();
        StringBuilder tmpResult = new StringBuilder(line);
        /*while(line  != null) {
            tmpResult.append(line);
        }*/
        //tmpResult.append(line);
        reader.close();
        //return line;
        //return tmpResult.toString();
        return new WeatherForecast(new JSONObject(tmpResult.toString()));

    }



    /*
    public static String getWeather(Context context, String pointId)

        throws IOException{
        HttpURLConnection client = (HttpURLConnection).newInstance(USER_AGENT, context);
        HttpGet get = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        try{
            HttpResponse response = client.execute(get);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String line = null;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        }finally{
            client.close();
        }
        return sb.toString();
    }
    */
}
