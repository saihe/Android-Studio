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
    //ChatLog.javaのインスタンス
    private ArrayList chatLog;
    //ログ一行分
    private String[] log;
    //ハンドラー
    private Handler handler;
    //アカウント
    private String user = "Kyontaro";



    //TextViewを追加するメソッド
    private void addTextView(String msg){
        //テキスト作成・挿入
        TextView tv = new TextView(this);
        tv.setText(msg);
        //ScrollViewの子のLinearLayoutのiDを取得しViewを追加
        ll = (LinearLayout) findViewById(R.id.sRelativeLayout);
        ll.addView(tv, i);
        i ++;
    }

    //ログ書き出し・読み込み
    //setは保存されたログのオブジェクト一覧に書き出す
    private String setAndGetLog(String[] log){
        try{
            final String[] set = connectLog.setLog(log);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    addTextView("set: " + set);
                }
            });
        }catch (IOException e){
            Log.e("", e.toString());
        }
        //getは保存されたログのオブジェクト一覧を出す
        try{
            final String get = connectLog.getLog();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    addTextView(get);
                }
            });
        } catch (IOException e) {
            Log.e("", e.toString());
        }
        return "Hoge";
    }


    //ログに接続
    final Thread connection = new Thread(){
        @Override
        public void run(){
            try{
                final String get = connectLog.connectLog();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addTextView(get);
                    }
                });
            } catch (IOException e) {
                Log.e("", e.toString());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ログに接続開始
        connection.start();
        //ID:submitボタンをpushSubmitという変数と関連付ける
        pushSubmit = (Button) findViewById(R.id.submit);
        //ID:editViewをevという変数に関連付ける
        ev = (EditText) findViewById(R.id.editText);
        //ハンドラーインスタンス
        handler = new Handler();


        //pushSubmitがクリックされたら検知し、検知したら実行
        pushSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //String型の変数に入力された文字を代入する
                    String str = ev.getText().toString();
                    //入力された文字・機器ID・時間を配列にする
                    log = ChatLog.createLog(user, str);
                    //空だったらtrue
                    //String le = String.valueOf(log.isEmpty());
                    //Log.d("", log);
                    ArrayList al = new ArrayList();
                    al.add(log);
                    setAndGetLog(log);
                    //入力フィールドを空にしてtextViewの文字を入力された文字に変更する
                    ev.setText("");
                    //TextVeiw作成・入力文字挿入・カウントアップ
                    addTextView("log:" + log[0] + "/" + log[1] + "/" + log[2]);
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
