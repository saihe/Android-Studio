package jp.co.july.tokyo.saitokyohei.sqlite2;

/**
 * Created by saito.kyohei on 2015/10/01.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.LoginFilter;
import android.util.Log;

public class MemoDao {
    //Dao はデータアクセスオブジェクト
    //Entity は(データの)実態

    // テーブルの定数
    private static final String TABLE_NAME = "memo";
    private static final String COLUMN_ID = "rowid";
    private static final String COLUMN_DATA = "data";
    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_DATA};

    // SQLiteDatabase
    private SQLiteDatabase db;

    /**
     * コンストラクタ
     * @param db
     */
    public MemoDao(SQLiteDatabase db) {
        Log.d("", "コンストラクタでクラスにデータベースを定義");
        this.db = db;
    }

    /**
     * 全データの取得   ----------------①
     * @return
     */
    public List<MyDBEntity> findAll() {
        Log.d("", "データベースのデータを List で実態化");
        List<MyDBEntity> entityList = new  ArrayList<MyDBEntity>();
        //queryは第一引数でSQL文、第二引数でWHERE句を記述(直接条件を記述するか ? を記述し)
        Log.d("", "query(テーブル名, IDとデータ, null * 4, ID)をcursorに格納");
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                null,
                null,
                null,
                null,
                COLUMN_ID);

        while(cursor.moveToNext()) {
            //moveToNext = 次のレコード
            Log.d("", "entity(実態)にIDとValueをセットしentityList.add(entity)");
            MyDBEntity entity = new MyDBEntity();
            entity.setRowId(cursor.getInt(0));
            entity.setValue(cursor.getString(1));
            entityList.add(entity);
        }

        return entityList;
    }

    /**
     * 特定IDのデータを取得   ----------------②
     * @param rowId
     * @return
     */
    public MyDBEntity findById(int rowId) {
        Log.d("", "rowIDからデータを取得する");
        String selection = COLUMN_ID + "=" + rowId;
        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null,
                null,
                null,
                null);

        cursor.moveToNext();
        MyDBEntity entity = new MyDBEntity();
        entity.setRowId(cursor.getInt(0));
        entity.setValue(cursor.getString(1));

        return entity;
    }

    /**
     * データの登録   ----------------③
     * @param data
     * @return
     */
    public long insert(String value) {
        Log.d("", "データの登録");
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA, value);
        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * データの更新   ----------------④
     * @param rowid
     * @param date
     * @return
     */
    public int update(MyDBEntity entity) {
        Log.d("", "データの更新");
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA, entity.getValue());
        String whereClause = COLUMN_ID + "=" + entity.getRowId();
        return db.update(TABLE_NAME, values, whereClause, null);
    }

    /**
     * データの削除   ----------------⑤
     * @param rowId
     * @return
     */
    public int delete(int rowId) {
        Log.d("", "データの削除");
        String whereClause = COLUMN_ID + "=" + rowId;
        return db.delete(TABLE_NAME, whereClause, null);
    }

}
