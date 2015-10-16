package jp.co.july.tokyo.saitokyohei.sqlite2;

/**
 * Created by saito.kyohei on 2015/10/01.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "memodb";

    /** 「dbtest」テーブルの作成用SQL */
    private static final String CREATE_TABLE_SQL = "" +
            "create table memo (" +
            "rowid integer primary key autoincrement, " +
            "data text not null " +
            ")";

    /** 「dbtest」テーブルの削除用SQL */
    private static final String DROP_TABLE_SQL = "drop table if exists memo";

    /**
     * コンストラクタ（必須）
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MyDBHelper(Context context, CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
        Log.d("", "MyDBHelper.35 MyDBHelperのコンストラクタ");
    }

    /**
     * テーブルの生成（必須）
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        Log.d("", "引数dbは MinActivity.java 41s行目で定義されている");
        Log.d("", "memo というテーブルを作る(IDはプライマリキーでA.I, データはテキストでnot null)");
        */
        Log.d("", "MyDBHelper.47 テーブル作成SQL: create table memo(rowid integer primary key autoincrement, data text not null)");
        db.execSQL(CREATE_TABLE_SQL);
    }

    /**
     * テーブルの再作成（必須）
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("", "MyDBHelper.56 テーブル削除: drop table if exists memo → memoがあればドロップ");
        db.execSQL(DROP_TABLE_SQL);
        Log.d("", "MyDBHelper.58 テーブル再作成");
        db.execSQL(CREATE_TABLE_SQL);
    }

}