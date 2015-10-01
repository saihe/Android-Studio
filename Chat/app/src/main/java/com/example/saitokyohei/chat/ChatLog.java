package com.example.saitokyohei.chat;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.SimpleTimeZone;

/**
 * Created by saito.kyohei on 2015/09/28.
 */
public class ChatLog {
    //String型の変数
    private static String logTime;
    //ログ一行分
    private static ArrayList<String> log;
    //全ログ
    private static HashMap<String, ArrayList<String>> chatLog = new HashMap<String, ArrayList<String>>();

    //現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.
    public static String getLogTime(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        logTime =  df.format(date);
        return logTime;
    }

    public static HashMap<String, ArrayList<String>> setChatLog(String user, String msg){
        //ログ一行分の配列
        log = new ArrayList<String>();
        String time = getLogTime();
        log.add(0, user);
        log.add(1, msg);
        chatLog.put(time, log);
        for (String key: chatLog.keySet()) {
            Log.d("", key + chatLog.get(key) + "\n");
        }
        return chatLog;
    }

}
