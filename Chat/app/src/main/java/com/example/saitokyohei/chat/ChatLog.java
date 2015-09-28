package com.example.saitokyohei.chat;

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
    private static String[] log = new String[3];


    //現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.
    public static String getLogTime(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        logTime =  df.format(date);
        return logTime;
    }

    protected static String[] createLog(String user, String str){
        //ログ一行分の配列
        logTime = getLogTime();
        log[0] = logTime;
        log[1] = user;
        log[2] = str;
        return log;
        //return str;
    }
}
