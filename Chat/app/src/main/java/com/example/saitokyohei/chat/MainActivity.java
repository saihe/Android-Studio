package com.example.saitokyohei.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.os.Handler;

public class MainActivity extends Activity {

    //Button型の変数
    private Button pushSubmit;
    //EditText型の変数
    private EditText ev;
    //RelativeLayout型の変数
    private LinearLayout ll;
    //int型の変数
    private int i = 0;
    //ログ一行分
    private ArrayList log;
    //ハンドラー
    private Handler handler;
    //アカウント
    private String user = "Kyontaro";



    /*
     *TextViewを追加するメソッド
     */
    private void addTextView(String msg){
        //テキスト作成・挿入
        TextView tv = new TextView(this);
        tv.setText(msg);
        //ScrollViewの子のLinearLayoutのiDを取得しViewを追加
        ll = (LinearLayout) findViewById(R.id.sRelativeLayout);
        ll.addView(tv, i);
        i ++;
    }

    /*
     *ログ書き出し・読み込み
     */
    private String setAndGetLog(ArrayList log){
        try{
            //setは保存されたログのオブジェクト一覧に書き出す
            final ArrayList set = connectLog.setLog(log);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    addTextView("set: " + set.get(0));
                    Log.d("", "Hoge7");
                }
            });
        }catch (IOException e){
            Log.e("", "setLogError: " + e.toString());
        }
        //getは保存されたログのオブジェクト一覧を出す
        try{
            final String get = connectLog.getLog();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    addTextView(get);
                    Log.d("", "Hoge8");
                }
            });
        } catch (IOException e) {
            Log.e("", "getLogError: " + e.toString());
        }
        return "Hoge";
    }

    /*
     *ログに接続
     */
    Thread connection = new Thread(){
        @Override
        public void run() {
            Log.d("", "接続スレ");
            try {
                final String connect = connectLog.connectLog();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addTextView(connect);
                    }
                });
            } catch (IOException e) {
                Log.e("", e.toString());
            }
        }
    };

    /*
     *ログ受け取り
     */
    Thread getLog = new Thread(){
        @Override
        public void run(){
            Log.d("", "ログ受け取りスレ");
            try{
                final String getLog = connectLog.getLog();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addTextView(getLog);
                    }
                });
            } catch (IOException e) {
                Log.e("", e.toString());
            }
        }
    };

    /*
     * ログ送信
     */
    Thread setLog = new Thread(){
        @Override
        public void run(){
            Log.d("", "ログ送信スレ");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ログに接続開始
        connection.start();

        getLog.start();
        //ID:submitボタンをpushSubmitという変数と関連付ける
        pushSubmit = (Button) findViewById(R.id.submit);
        //ID:editViewをevという変数に関連付ける
        ev = (EditText) findViewById(R.id.editText);
        //ハンドラーインスタンス
        handler = new Handler();

        /*
         *pushSubmitがクリックされたら検知し、検知したら実行
         */
        pushSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "Hoge1");
                try {
                    //ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //String型の変数に入力された文字を代入する
                    String str = ev.getText().toString();
                    Log.d("", "Hoge2");
                    //入力された文字・機器ID・時間を配列にする
                    //log.add(ChatLog.setChatLog(user, str));
                    log = ChatLog.setChatLog(user, str);
                    Log.d("", "Hoge5");
                    Log.d("", log.get(0).toString());
                    //String get = connectLog.getLog();
                    //ログに配列を書き込む・呼び出す
                    setAndGetLog(log);
                    Log.d("", "Hoge9");
                    //入力フィールドを空にしてtextViewの文字を入力された文字に変更する
                    ev.setText("");
                    Log.d("", "Hoge10");
                    //TextVeiw作成・入力文字挿入・カウントアップ
                    //addTextView("log:" + log.get(0) + "/" + log.get(1) + "/" + log.get(2));
                    //addTextView("get: " + get);
                    addTextView("str: " + str);
                    Log.d("", "Hoge11");
                } catch (Exception e) {
                    //エラーメッセージを出す
                    String emsg = e.toString();
                    Log.e("", emsg);
                    addTextView("View error: " + emsg);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
