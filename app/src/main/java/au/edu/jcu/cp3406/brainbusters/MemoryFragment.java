package au.edu.jcu.cp3406.brainbusters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
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
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = (float)displayMetrics.widthPixels;
        float screenHeight = (float) ((float)displayMetrics.heightPixels * 0.8);
        float screenRatio = screenHeight / screenWidth;
        float screenArea = screenWidth * screenHeight;
        float cardArea = (float) ((screenArea)/(Memory.DECK_SIZE));
        float cardHeight = (float)Math.sqrt(screenRatio * cardArea);
        float cardWidth = cardArea/cardHeight;

        int colSize = (int) (screenWidth/cardWidth);
        int rowSize = (int) (screenHeight/cardHeight);
        int index = 0;
        for(int row = 0; row < rowSize && index < colSize*rowSize; row++){
            for(int col = 0; col < colSize && index < colSize*rowSize; col++){
                ImageView cardView = new ImageView(memoryGrid.getContext());
                cardView.setImageBitmap(imageManager.getCardImage(memory.getCard(index)));
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) cardWidth;
                params.height = (int) cardHeight;
                params.columnSpec = GridLayout.spec(col);
                params.rowSpec = GridLayout.spec(row);
                cardView.setLayoutParams(params);
                memoryGrid.addView(cardView);
                index++;
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBooleanArray("state",memory.saveState());
    }
}
