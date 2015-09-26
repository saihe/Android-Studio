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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    //Button型の変数
    private Button pushSubmit;
    //EditText型の変数
    private EditText ev;
    //RelativeLayout型の変数
    private LinearLayout ll;
    //int型の変数
    private int i = 0;
    //String型の変数
    private String logTime;
    //String型のURL
    private static String URL = "http://drive.google.com/drive/folders/0B3s2I7Ksr9ZmTk1BTFlpS3F4cEE";


    //現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.
    public String getLogTime(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        logTime =  df.format(date);
        return logTime;
    }

    //TextViewを追加するメソッド
    private void addTextView(String msg){
        //テキスト作成・挿入
        TextView tv = new TextView(this);
        tv.setText(msg);
        //ScrollViewの子のLinearLayoutのiDを取得しViewを追加
        ll = (LinearLayout) findViewById(R.id.sRelativeLayout);
        ll.addView(tv);
    }

    //Googleドライブに接続するメソッド
    private static String getConnection() throws IOException {
        //リクエスト送信
        URL requestUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        /*InputStream input = connection.getInputStream();

        // 結果取得
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                input, "UTF-8"));
        String line = reader.readLine();
        StringBuilder tmpResult = new StringBuilder(line);

        reader.close();*/
        //return line;これでもOKっぽい
        //return tmpResult.toString();
        return "Hoge";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ログにonCreate()の処理を表示する
        Log.d("", "onCreate()");

        //ID:submitボタンをpushSubmitという変数と関連付ける
        pushSubmit = (Button) findViewById(R.id.submit);
        //ID:editViewをevという変数に関連付ける
        ev = (EditText) findViewById(R.id.editText);

        //pushSubmitがクリックされたら検知し、検知したら実行
        pushSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //LogTime入手
                    logTime = getLogTime();
                    //ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //String型の変数に入力された文字を代入する
                    String str = ev.getText().toString();
                    //入力フィールドを空にしてtextViewの文字を入力された文字に変更する
                    ev.setText("");
                    //TextVeiw作成・入力文字挿入・カウントアップ
                    addTextView(str);
                    addTextView(logTime);
                    try {
                        getConnection();
                        addTextView("Connect!");
                    }catch (Exception e){
                        addTextView("Hoge");
                    }
                    i++;
                }catch (Exception e){
                    //TextView作成・Hoge挿入
                    addTextView("Error!");
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
