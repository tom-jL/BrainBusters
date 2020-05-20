package au.edu.jcu.cp3406.brainbusters.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import au.edu.jcu.cp3406.brainbusters.AudioManager;
import au.edu.jcu.cp3406.brainbusters.MainActivity;
import au.edu.jcu.cp3406.brainbusters.R;
import au.edu.jcu.cp3406.brainbusters.ShakeSensor;
import au.edu.jcu.cp3406.brainbusters.StatsDatabaseHelper;
import au.edu.jcu.cp3406.brainbusters.models.Memory;
import au.edu.jcu.cp3406.brainbusters.models.Soduku;
import au.edu.jcu.cp3406.brainbusters.views.NumberView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SodukuFragment extends Fragment implements View.OnFocusChangeListener {

    private Soduku soduku;
    private float numberViewWidth;
    private GridLayout sodukuGrid;
    private Handler handler;
    private Runnable restart;


    private static long dataBaseID = 0;

    public SodukuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        restart = new Runnable() {
            @Override
            public void run() {
                soduku = new Soduku();
                buildGrid();
            }
        };
        soduku = new Soduku();
        if (savedInstanceState != null) {
            soduku.loadState(savedInstanceState.getIntArray("state"));
        } else {
            soduku.newGame();
            soduku.setDifficulty(Soduku.Difficulty.easy);
        }

        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("SodukuFragment.onCreate","Display is Landscape");
            float screenHeight = (float) displayMetrics.heightPixels;
            numberViewWidth = (float) ((screenHeight * 0.8) / 9);
        } else {
            float screenWidth = (float) displayMetrics.widthPixels;
            numberViewWidth = screenWidth / 9;
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soduku, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            sodukuGrid = view.findViewById(R.id.sodukuGrid);
            buildGrid();
        }
    }

    private void buildGrid() {
        sodukuGrid.removeAllViewsInLayout();
        for (int row = 0; row < soduku.getGame().length; row++) {
            for (int col = 0; col < soduku.getRow(row).length; col++) {
                NumberView numberView = new NumberView(sodukuGrid.getContext(),soduku.getCell(row, col), row, col);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) numberViewWidth;
                params.height = (int) numberViewWidth;
                params.columnSpec = GridLayout.spec(col);
                params.rowSpec = GridLayout.spec(row);
                numberView.setLayoutParams(params);
                numberView.setTextSize((float) (numberViewWidth*0.2));
                numberView.setOnFocusChangeListener(this);
                sodukuGrid.addView(numberView);
            }
        }
        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            float screenWidth = (float) displayMetrics.widthPixels;
            ((ViewPager)sodukuGrid.getParent()).setPageMargin((int) (screenWidth/2-((numberViewWidth*9)/2)));
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntArray("state", soduku.saveState());
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            NumberView numberView = (NumberView) v;
            soduku.setCell(numberView.getRow(), numberView.getCol(), numberView.getNumber());
        } else {
            if (soduku.isValid()) {
                Log.i("State", "You have solved the puzzle.");
                ((MainActivity)getActivity()).updateStat(dataBaseID);
                ((MainActivity)getActivity()).playWin();
                handler.postDelayed(restart, 1000);


            } else {
                Log.i("State", soduku.toString());
            }
        }
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}

