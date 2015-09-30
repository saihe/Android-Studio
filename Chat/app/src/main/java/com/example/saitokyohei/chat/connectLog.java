package com.example.saitokyohei.chat;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by saito.kyohei on 2015/09/28.
 */
public class ConnectLog {

    //String型のURL Googleドライブ内のChatLogファイルのURL
    //private static String URL = "http://saihe.sakura.ne.jp/Log.txt";
    private static String URL = "aa";
    //private static String URL = "https://drive.google.com/open?id=0B3s2I7Ksr9ZmaVd1TXI2aDhQbEE";
    //private static String URL = "https://drive.google.com/file/d/0B3s2I7Ksr9ZmaVd1TXI2aDhQbEE/view?usp=sharing";
    //private static String URL = "https://drive.google.com/file/d/0B3s2I7Ksr9ZmaVd1TXI2aDhQbEE";
    //private static String URL = "https://drive.google.com/file/d/0B3s2I7Ksr9ZmaVd1TXI2aDhQbEE/view?ts=560b6bfe";


    protected static String connectLog() throws IOException {
        //リクエスト送信
        URL requestUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        //POST可能にする
        connection.setDoOutput(true);
        return "Start connection";
    }


    //ファイルに接続・読み込むメソッド(現状、ファイルの中身の文字列そのまま)
    protected static String getLog() throws IOException {
        try {
            //リクエスト送信
            URL requestUrl = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            //POST可能にする
            //connection.setDoOutput(true);
            // 結果取得
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String line = reader.readLine();
            StringBuilder tmpResult = new StringBuilder(line);
            while (line != null) {
                line = reader.readLine();
                tmpResult.append(line);
            }
            reader.close();
            //return line;//これでもOKっぽい
            //Log.i("", tmpResult.toString());
            Log.d("", "GetLog: " + tmpResult.toString());
            return tmpResult.toString();
            //return "Get Log";
        }catch (IOException e){
            try {
                Log.d("", "GetLog開始");
                FileInputStream input = new FileInputStream("chatLog.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                String line = reader.readLine();
                StringBuilder tmpResult = new StringBuilder(line);
                while (line != null){
                    line = reader.readLine();
                    tmpResult.append(line);
                }
                reader.close();
                return tmpResult.toString();
            }catch (IOException e2){
                Log.d("", "GetError2");
                return "GetError2";
            }

        }

    }

    private static boolean checkBeforeWritefile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canWrite()) {
                return true;
            }
        }
        return false;
    }

    protected static HashMap<String, ArrayList<String>> setLog(HashMap<String, ArrayList<String>> log) throws IOException {
        try {
            URL requestUrl = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            OutputStream output = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            for (String key: log.keySet()) {
                writer.write(key);
                writer.write(log.get(key).toString());
                writer.write(log.get(key).toString());
            }
            File file = new File(URL);
            if (checkBeforeWritefile(file)) {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                for (String key: log.keySet()) {
                    writer.write(key);
                    writer.write(log.get(key).toString());
                    writer.write(log.get(key).toString());
                }
                pw.close();
            } else {
                Log.e("", "ファイルに書き込めません");
            }
        } catch (IOException e) {
            try{
                File file = new File("chatLog.txt");
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                Log.d("", "pw直後");
                for(String key: log.keySet()){
                    pw.write(key);
                    pw.write(log.get(key).toString());
                    pw.write(log.get(key).toString());
                }
                Log.d("", "Setできた？？");
                pw.close();
                return log;
            }catch (IOException e2){
                Log.d("", "SetError2");
                return log;
            }
        }
        return log;
    }
}
