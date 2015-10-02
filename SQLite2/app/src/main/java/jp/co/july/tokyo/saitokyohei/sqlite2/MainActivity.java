package jp.co.july.tokyo.saitokyohei.sqlite2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {
    private String logTime = "";
    private MemoDao dao;
    private LinearLayout showData;
    private EditText dataEdit;
    private Button button;
    private String USER = "Kyontaro";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQLiteの準備
        Log.d("", "MyDBHelperをインスタンス");
        MyDBHelper helper = new MyDBHelper(this, null, 1);
        Log.d("", "SQLiteDatabase db を定義");
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.d("", "MemoDaoに db を渡してインスタンス");
        dao = new MemoDao(db);

        // データ入力欄の初期設定
        dataEdit = (EditText)findViewById(R.id.dataEdit);
        dataEdit.setOnKeyListener(new AddressBarOnKeyListener());

        // データ出力Viewグループの取得
        showData = (LinearLayout)findViewById(R.id.showData);

        // 表示データの更新
        changeData();

        /*
        button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new buttonClick());
        */
    }

    /**
     * 追加ボタンのクリックイベント
     * @param view
     */
    public void addData(View view) {

        Log.d("", "MainActivity.68 DBへ渡すデータ作成");
        // データの追加
        //現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.
        Log.d("", "MainActivity.71 日付取得");
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        logTime =  df.format(date);
        Log.d("", "logTime: " + logTime);
        Log.d("", "MainActivity.76 データ作成、日付・入力者・入力文字");
        final ContentValues values = new ContentValues();
        values.put("Time", logTime);
        values.put("Name", USER);
        values.put("Mess", dataEdit.getText().toString());
        Log.d("", "values: " + values);
        dao.insert(values.toString());

        // 入力欄のクリア
        Log.d("", "MainActivity.85 入力欄クリア");
        dataEdit.setText(null);

        // 表示データの更新
        changeData();
    }


    /**
     * 表示データの更新
     */
    private void changeData() {

        Log.d("", "MainActivity.98 表示データ更新開始");
        // 表示中のデータを一旦すべてクリアする。
        Log.d("", "MainActivity.100 表示欄クリア");
        showData.removeAllViews();

        // DBからすべてのデータを取得する。
        Log.d("", "MainActivity.104 DBからデータ取得");
        List<MyDBEntity> entityList = dao.findAll();

        // データを表示領域に追加する
        Log.d("", "MainActivity.108 取得したデータを一行ずつ下に追加");
        for(MyDBEntity entity: entityList) {
            TextView textView = new TextView(this);
            textView.setText(entity.getRowId() + "： " + entity.getValue());
            showData.addView(textView);
        }
    }


    // ソフトキーボードの「確定」が押された時にソフトキーボードを消す
    private class AddressBarOnKeyListener implements View.OnKeyListener {

        public boolean onKey(View view, int keyCode, KeyEvent event) {

            Log.d("", "MainActivity.122 [Enter]を判定");
            //EnterKeyが押されたかを判定
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_ENTER) {

                Log.d("", "MainActivity.127 キーボードを閉じる");
                //ソフトキーボードを閉じる
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            }

            return false;
        }
    }

    /*
    private class buttonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //ソフトキーボードを非表示にする
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            changeData();
        }
    }
    */

    /*
    削除メソッド
    */
    /*
    public int delete(int rowId) {
        String whereClause = COLUMN_ID + "=" + rowId;
        return db.delete(TABLE_NAME, whereClause, null);
    }
    */

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
