package jp.co.july.tokyo.weatherforcassts;

/**
 * Created by saito.kyohei on 2015/09/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoaderTask extends AsyncTask<ImageView, Void, Bitmap>{

    private final Context context;

    Exception exception;
    ImageView imageView;

    public ImageLoaderTask(Context context){
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(ImageView... params){
        imageView = params[0];
        String url = (String) imageView.getTag();
        try{
            return getImage(url);
        }catch (IOException e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
    }

    private static Bitmap getImage(String uri) throws IOException{
        //Httpにリクエスト・レスポンス取得
        URL requestUrl = new URL(uri);
        HttpURLConnection connection;
        connection = (HttpURLConnection) requestUrl.openConnection();//HttpGetと同じ
        InputStream input = connection.getInputStream();//HttpResponseと同じ
        Bitmap response = BitmapFactory.decodeStream(input);
        input .close();
        return response;
    }

}
