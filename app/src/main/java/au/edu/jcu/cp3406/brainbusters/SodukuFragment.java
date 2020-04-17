package au.edu.jcu.cp3406.brainbusters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import au.edu.jcu.cp3406.brainbusters.models.Soduku;


/**
 * A simple {@link Fragment} subclass.
 */
public class SodukuFragment extends Fragment {

    Soduku soduku;

    public SodukuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            soduku.set1DArray(savedInstanceState.getIntArray("game"));
        }
        soduku = new Soduku();
        soduku.setDifficulty(Soduku.Difficulty.easy);

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
        if (view != null){
            GridLayout sodukuGrid = (GridLayout) view.findViewById(R.id.sodukuGrid);
            buildGrid(sodukuGrid);
        }
    }

    void buildGrid(GridLayout sodukuGrid){
        sodukuGrid.removeAllViewsInLayout();
        for(int row = 0; row < soduku.getGame().length; row++){
            for(int col = 0; col < soduku.getRow(row).length; col++){
                if(soduku.getCell(row, col) == 0){
                    //TODO: Make button for number selection
                    TextView numberView = new TextView(sodukuGrid.getContext());
                    numberView.setText(String.valueOf(soduku.getCell(row, col)));
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 80;
                    params.height = 80;
                    params.columnSpec = GridLayout.spec(col);
                    params.rowSpec = GridLayout.spec(row);
                    numberView.setLayoutParams(params);
                    sodukuGrid.addView(numberView);
                }else{
                    TextView numberView = new TextView(sodukuGrid.getContext());
                    numberView.setText(String.valueOf(soduku.getCell(row, col)));
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 80;
                    params.height = 80;
                    params.columnSpec = GridLayout.spec(col);
                    params.rowSpec = GridLayout.spec(row);
                    numberView.setLayoutParams(params);
                    sodukuGrid.addView(numberView);
                }
            }
        }
    }





    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntArray("game",soduku.get1DArray());
    }

}

