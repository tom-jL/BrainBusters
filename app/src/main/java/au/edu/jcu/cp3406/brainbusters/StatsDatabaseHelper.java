package au.edu.jcu.cp3406.brainbusters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StatsDatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "STATS";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "STAT";

    public StatsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STAT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "COUNT INTEGER);"
        );
        //db = getWritableDatabase();
        ContentValues stat = new ContentValues();
        stat.put("_id", 0);
        stat.put("COUNT", 0);
        db.insert("STAT", null, stat);
        stat = new ContentValues();
        stat.put("_id", 1);
        stat.put("COUNT", 0);
        db.insert("STAT", null, stat);
        stat = new ContentValues();
        stat.put("_id", 2);
        stat.put("COUNT", 0);
        db.insert("STAT", null, stat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void insertStat(long id) {
        Log.i("StatsDatabaseHelper","Inserting new Statistic");
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.execSQL("UPDATE " + "STAT" + " SET " + "COUNT" + "=" + "COUNT" + "+1" + " WHERE " + "_id = ?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e) {

        }
        db.close();
    }


}

