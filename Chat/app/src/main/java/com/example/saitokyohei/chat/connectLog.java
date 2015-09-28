package com.example.saitokyohei.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by saito.kyohei on 2015/09/28.
 */
public class connectLog {

    //String型のURL Googleドライブ内のChatLogファイルのURL
    private static String URL = "https://drive.google.com/file/d/0B3s2I7Ksr9ZmUUxOSzhZc2h1cG8/view?usp=sharing";

    protected static String connectLog() throws IOException {
        //リクエスト送信
        URL requestUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        //POST可能にする
        connection.setDoOutput(true);
        return "Start connection";
    }

    //Googleドライブに接続・読み込むメソッド
    protected  static String getLog() throws IOException {
        /*//リクエスト送信
        URL requestUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        //POST可能にする
        connection.setDoOutput(true);*/

        // 結果取得
        /*InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                input, "UTF-8"));
        String line = reader.readLine();
        StringBuilder tmpResult = new StringBuilder(line);

        reader.close();
        //return line;これでもOKっぽい
        return tmpResult.toString();*/
        return "Get Log";
    }

    protected static String[] setLog(String[] log) throws IOException {
        /*URL requestUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        //POST可能にする
        connection.setDoOutput(true);*/
        /*// ヘッダを設定
        connection.setRequestProperty("User-Agent", "@IT java-tips URLConnection");
        connection.setRequestProperty("Accept-Language", "ja");
        //POST用のOutputStreamを取得
        OutputStream output = connection.getOutputStream();
        //POSTするデータ
        String postStr = "post1=bar1&post2=bar2";
        PrintStream ps = new PrintStream(output);
        //データをPOSTする
        ps.print(postStr);
        ps.close();
        ps.close();*/

        /*// 結果取得
        InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                input, "UTF-8"));
        String line = reader.readLine();
        StringBuilder tmpResult = new StringBuilder(line);

        reader.close();*/
        //return line;これでもOKっぽい
        //return tmpResult.toString();
        return log;
    }
}
