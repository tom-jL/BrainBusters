package au.edu.jcu.cp3406.brainbusters.fragments;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import au.edu.jcu.cp3406.brainbusters.R;
import au.edu.jcu.cp3406.brainbusters.models.Soduku;
import au.edu.jcu.cp3406.brainbusters.views.NumberView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SodukuFragment extends Fragment {

    Soduku soduku;
    float numberViewWidth;

    public SodukuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soduku = new Soduku(Soduku.Difficulty.easy);
        if (savedInstanceState != null) {
            soduku.loadState(savedInstanceState.getIntArray("state"));
        } else {
            soduku.newGame();
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = (float) displayMetrics.widthPixels;
        numberViewWidth = screenWidth / 9;


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
            GridLayout sodukuGrid = view.findViewById(R.id.sodukuGrid);
            buildGrid(sodukuGrid);
        }
    }

    void buildGrid(GridLayout sodukuGrid) {
        sodukuGrid.removeAllViewsInLayout();
        for (int row = 0; row < soduku.getGame().length; row++) {
            for (int col = 0; col < soduku.getRow(row).length; col++) {
                NumberView numberView = new NumberView(sodukuGrid.getContext(),soduku.getCell(row, col));
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) numberViewWidth;
                params.height = (int) numberViewWidth;
                params.columnSpec = GridLayout.spec(col);
                params.rowSpec = GridLayout.spec(row);
                numberView.setLayoutParams(params);
                sodukuGrid.addView(numberView);
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntArray("state", soduku.saveState());
    }

}

