package com.example.saitokyohei.chat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
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
    //TextView型の変数
    private TextView tv;
    //RelativeLayout型の変数
    private LinearLayout ll;
    //int型の変数
    private int i = 0;
    //ログ一行分
    private ArrayList<String> log = new ArrayList<String>();
    //全ログ
    private HashMap<String, ArrayList<String>> chatLog = new HashMap<String, ArrayList<String>>();
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

    private void editTextview(ArrayList<String> log){
        TextView tv = new TextView(this);
        //tv = (TextView) findViewById(R.id.textView);
        for(int k = 0; k < log.size(); k++){
            tv.setText(log.get(k) + "\n");
            i ++;
            ll = (LinearLayout) findViewById(R.id.sRelativeLayout);
            ll.addView(tv, i);
        }
    }

    private void inputClick(){
        try{
            Log.d("", "読み込み開始");
            FileInputStream in = openFileInput( "test.txt" );
            Log.d("", "test.txtを読み込み");
            BufferedReader reader = new BufferedReader( new InputStreamReader( in , "UTF-8") );
            String tmp;
            while( (tmp = reader.readLine()) != null ){
                Log.d("", tmp);
                log.add(tmp);
                editTextview(log);
            }
            Log.d("", "LogCatとTextViewに書き出し");
            reader.close();
        }catch( IOException e ){
            e.printStackTrace();
            Log.e("", "読み込み失敗");
        }
    }

    private void outputClick(){
        //保存
        try{
            Log.d("", "保存開始");
            FileOutputStream out = openFileOutput( "test.txt", MODE_PRIVATE );
            Log.d("", "test.txtを読み込み");
            for(String key: chatLog.keySet()){
                out.write(key.getBytes());
                out.write(chatLog.get(key).get(0).getBytes());
                out.write(chatLog.get(key).get(1).getBytes());
            }
            Log.d("", "書き出し");
        }catch(IOException e ){
            e.printStackTrace();
            Log.e("", "保存失敗");
        }
    }

    /*
     *ログ書き出し・読み込み
     */
    private  String setAndGetLog(){
        try{
            //setは保存されたログのオブジェクト一覧に書き出す
            SetLog set = new SetLog();
            set.start();

        }catch (Exception e){
            Log.e("", "setLogError: " + e.toString());
        }
        //getは保存されたログのオブジェクト一覧を出す
        try{
            GetLog get = new GetLog();
            get.start();
        } catch (Exception e) {
            Log.e("", "getLogError: " + e.toString());
        }
        return "SetAndGet";
    }

    /*
     *接続テスト用
     */
    public class Connection extends Thread{
        private boolean halt_;

        public Connection(){
            halt_ = false;
            Log.d("", "connectionhalt_ == false");
        }

        public void run() {
            Log.d("", "接続スレ");
            while (!halt_){
                try {
                    final String connect = ConnectLog.connectLog();
                    Log.d("", connect);
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
        }

        public void halt(){
            halt_ = true;
            interrupt();
            Log.d("", "StopConnection");
        }
    }

    /*
     *ログ受け取り
     */
    public class GetLog extends Thread{
        private boolean halt_;

        public GetLog(){
            halt_ = false;
            Log.d("", "gethalt_ == false");
        }

        public void run() {
            Log.d("", "ログ受け取りスレ");
            do {
                try{
                    final String get = ConnectLog.getLog();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            addTextView(get);
                        }
                    });
                    halt();
                } catch (IOException e) {
                    Log.e("", e.toString());
                }
            }while (!halt_);
        }

        public void halt(){
            halt_ = true;
            interrupt();
            Log.d("", "StopGetLog");
        }
    }

    /*
     * ログ送信
     */
    public class SetLog extends Thread{
        private boolean halt_;

        public SetLog(){
            halt_ = false;
            Log.d("", "sethalt_ == false");
        }

        public void run() {
            Log.d("", "ログ送信スレ");
            do{
                try{
                    ConnectLog.setLog(chatLog);
                    halt();
                } catch (IOException e) {
                    Log.e("", e.toString());
                }
            }while (!halt_);
        }

        public void halt(){
            halt_ = true;
            interrupt();
            Log.d("", "StopGetLog");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ログに接続開始
        /*GetLog getLog =new GetLog();
        getLog.start();*/
        log.add("Chat");
        inputClick();
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
                try {
                    //ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //String型の変数に入力された文字を代入する
                    String str = ev.getText().toString();
                    //入力された文字・機器ID・時間を配列にする
                    chatLog = ChatLog.setChatLog(user, str);
                    /*
                     *追加したやつ
                     */
                    //保存
                    outputClick();
                    //読み込み
                    inputClick();
                    /*
                     *ここまで
                     */
                    //ログに配列を書き込む・呼び出す
                    //setAndGetLog();
                    //入力フィールドを空にしてtextViewの文字を入力された文字に変更する
                    ev.setText("");
                    //TextVeiw作成・入力文字挿入・カウントアップ

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

    //テキスト保存
    class InputClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String str = ev.getText().toString();
            try{
                FileOutputStream out = openFileOutput( "chatLog.txt", MODE_PRIVATE );
                out.write( str.getBytes()   );
            }catch( IOException e ){
                e.printStackTrace();
            }
        }
    }
}
