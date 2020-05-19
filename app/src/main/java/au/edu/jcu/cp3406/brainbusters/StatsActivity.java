package au.edu.jcu.cp3406.brainbusters;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StatsActivity extends AppCompatActivity {

    int[] stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStats();

        ((TextView)findViewById(R.id.sodukuStat)).setText(String.valueOf(stats[0]));
        ((TextView)findViewById(R.id.mineStat)).setText(String.valueOf(stats[1]));
        ((TextView)findViewById(R.id.memoryStat)).setText(String.valueOf(stats[2]));
    }

    public void getStats(){
        stats = new int[3];
        int id = 0;
        SQLiteOpenHelper statsDatabaseHelper = new StatsDatabaseHelper(this);
        try{
            SQLiteDatabase db = statsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(StatsDatabaseHelper.TABLE_NAME,
                    null, null,null,null,null,null);
            if (cursor.moveToFirst()){
                stats[id] = cursor.getInt(1);
                while(cursor.moveToNext()){
                    stats[++id] = cursor.getInt(1);
                }
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void backToMain(View view) {
        finish();
    }

    public void shareStats(View view) {
    }
}
