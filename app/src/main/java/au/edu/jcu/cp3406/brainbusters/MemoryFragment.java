package au.edu.jcu.cp3406.brainbusters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import au.edu.jcu.cp3406.brainbusters.models.Card;
import au.edu.jcu.cp3406.brainbusters.models.Memory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryFragment extends Fragment {

    Memory memory;
    ImageManager imageManager;
    String color = "gray";

    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memory = new Memory();
        if (savedInstanceState != null){
            memory.loadState(savedInstanceState.getBooleanArray("state"));
        } else {
            memory.shuffleCards();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false);

    }
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null){
            imageManager = new ImageManager(view.getContext().getAssets(), color);
            GridLayout memoryGrid = (GridLayout) view.findViewById(R.id.memoryGrid);
            buildGrid(memoryGrid);
        }
    }

    void buildGrid(GridLayout memoryGrid) {
        memoryGrid.removeAllViewsInLayout();
        int col = 0;
        int row = 0;
        for(Card card : memory.getCards()){
            if (col > 52/4){
                row++;
                col = 0;
            }
            ImageView cardView = new ImageView(memoryGrid.getContext());
            cardView.setImageBitmap(imageManager.getCardImage(card));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            cardView.setMaxHeight(320);
            cardView.setMaxWidth(160);
            params.width = 160;
            params.height = 320;
            params.columnSpec = GridLayout.spec(col);
            params.rowSpec = GridLayout.spec(row);
            cardView.setLayoutParams(params);
            memoryGrid.addView(cardView);
            col++;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBooleanArray("state",memory.saveState());
    }
}
