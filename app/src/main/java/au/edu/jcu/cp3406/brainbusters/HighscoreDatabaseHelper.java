package au.edu.jcu.cp3406.brainbusters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighscoreDatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "highscores";
    private static final int DB_VERSION = 1;

    HighscoreDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCORE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "GAME INTEGER, "
                + "TIME INTEGER, "
                + "DIFFICULTY INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    void insertScore(int game, int time, int difficulty) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues score = new ContentValues();
        score.put("GAME", game);
        score.put("TIME", time);
        score.put("DIFFICULTY", difficulty);
        long id = db.insert("SCORE", null, score);
        db.close();
    }


}

