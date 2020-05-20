package au.edu.jcu.cp3406.brainbusters;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

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

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("COJmbfyLrsQtN3zmESIzXjZQf")
                .setOAuthConsumerSecret("Yd4Sz5d3GPCEZNdujnIIxHJaiwIVpUZWlKDCOkJg9QF0MpNOo6")
                .setOAuthAccessToken("1250966422458527745-XBMhOAaSDDUHggUfWwNxVpzm31znBd")
                .setOAuthAccessTokenSecret("Z8SM49sY8fFdHu6flZT5GTzzxFrLlAQHHYbx6pwrJtz37");
        TwitterFactory tf = new TwitterFactory(cb.build());
        final Twitter twitter = tf.getInstance();

        final String string = "I've solved " + stats[0] +" Soduku puzzles, "+stats[1]+" Minesweeper puzzles and found "+stats[2]+" pairs of cards. -BrainBuster";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("twitter",twitter.getOAuthAccessToken().toString());
                    twitter.updateStatus(string);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
