package au.edu.jcu.cp3406.brainbusters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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
    GridLayout memoryGrid;
    int colSize;
    int rowSize;
    float cardHeight;
    float cardWidth;

    CardView guessCard;

    Handler cardHandler;

    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardHandler = new Handler();
        guessCard = null;
        memory = new Memory();
        if (savedInstanceState != null){
            memory.loadState(savedInstanceState.getBooleanArray("state"));
        } else {
            memory.shuffleCards();
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = (float)displayMetrics.widthPixels;
        float screenHeight = (float) ((float)displayMetrics.heightPixels * 0.8);
        float screenRatio = screenHeight / screenWidth;
        float screenArea = screenWidth * screenHeight;
        float cardArea = (float) ((screenArea)/(Memory.DECK_SIZE));
        cardHeight = (float)Math.sqrt(screenRatio * cardArea);
        cardWidth = cardArea/cardHeight;
        colSize = (int) (screenWidth/cardWidth);
        rowSize = (int) (screenHeight/cardHeight);



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
            imageManager = new ImageManager(view.getContext().getAssets());
            memoryGrid = (GridLayout) view.findViewById(R.id.memoryGrid);
            buildGrid();
        }
    }

    void buildGrid() {
        memoryGrid.removeAllViewsInLayout();
        int index = 0;
        for(int row = 0; row < rowSize && index < colSize*rowSize; row++){
            for(int col = 0; col < colSize && index < colSize*rowSize; col++){
                final CardView cardView = new CardView(memoryGrid.getContext(), memory.getCard(index), imageManager);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) cardWidth;
                params.height = (int) cardHeight;
                params.columnSpec = GridLayout.spec(col);
                params.rowSpec = GridLayout.spec(row);
                cardView.setLayoutParams(params);

                final int finalIndex = index;
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CardView cardView = (CardView) v;
                        selectCard(cardView);
                    }
                });

                memoryGrid.addView(cardView);
                index++;
            }
        }
    }

    public void selectCard(final CardView cardView){
        final Card card = cardView.getCard();
        if(cardView != guessCard && !card.isPaired()){
            cardView.showCard();
            if(guessCard == null){
                guessCard = cardView;
            }else{
                if(card.compareTo(guessCard.getCard()) == 0){
                    card.setPaired(true);
                    guessCard.getCard().setPaired(true);
                } else {
                    final CardView flipCard = guessCard;
                    Runnable hideCards = new Runnable() {
                        @Override
                        public void run() {
                            flipCard.hideCard();
                            cardView.hideCard();
                        }
                    };
                    cardHandler.postDelayed(hideCards, 2000);
                }
                guessCard = null;
            }
        }

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBooleanArray("state",memory.saveState());
    }
}
