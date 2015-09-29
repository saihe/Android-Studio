package com.example.saitokyohei.chat;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by saito.kyohei on 2015/09/28.
 */
public class ChatLog {
    //String型の変数
    private static String logTime;
    //ログ一行分
    private static ArrayList<String> log = new ArrayList<String>();


    //現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.
    public static String getLogTime(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        logTime =  df.format(date);
        return logTime;
    }

    public static ArrayList<String> setChatLog(String user, String msg){
        //ログ一行分の配列
        String time = getLogTime();
        Log.d("", "Hoge3");
        log.add(0, time);
        log.add(1, user);
        log.add(2, msg);
        Log.d("", "LogArray: " + log.get(0) + "/" + log.get(1) + "/" + log.get(2));
        Log.d("", "Hoge4");
        return log;
    }
}
