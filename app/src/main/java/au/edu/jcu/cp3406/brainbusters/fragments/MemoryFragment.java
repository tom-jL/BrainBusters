package au.edu.jcu.cp3406.brainbusters.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import au.edu.jcu.cp3406.brainbusters.ImageManager;
import au.edu.jcu.cp3406.brainbusters.MainActivity;
import au.edu.jcu.cp3406.brainbusters.R;
import au.edu.jcu.cp3406.brainbusters.ShakeSensor;
import au.edu.jcu.cp3406.brainbusters.models.Card;
import au.edu.jcu.cp3406.brainbusters.models.Memory;
import au.edu.jcu.cp3406.brainbusters.views.CardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryFragment extends Fragment {

    private Memory memory;
    private ImageManager imageManager;
    private GridLayout memoryGrid;
    private int colSize;
    private int rowSize;
    private float cardHeight;
    private float cardWidth;

    private CardView guessCard;
    private Handler cardHandler;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeSensor shakeSensor;

    private static long dataBaseID = 2;

    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardHandler = new Handler();
        guessCard = null;
        memory = new Memory();
        if (savedInstanceState != null) {
            memory.loadState(savedInstanceState.getIntArray("orderState"), savedInstanceState.getBooleanArray("pairState"));
        } else {
            memory.shuffleCards();
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float screenWidth = (float) displayMetrics.widthPixels;
        float screenHeight = (float) displayMetrics.heightPixels;
        int orientation = getResources().getConfiguration().orientation;
        cardWidth = screenWidth / 5;
        cardHeight = (float) ((screenHeight * 0.8) / 5);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("MemoryFragment.onCreate", "Orientation Landscape");
            cardWidth = screenWidth / 13;
            cardHeight = (float) (screenHeight * 0.8 / 3);
            colSize = 2;
            rowSize = 13;
        } else {
            rowSize = 5;
            colSize = 5;
        }

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeSensor = new ShakeSensor();
        sensorManager.registerListener(shakeSensor, accelerometer, SensorManager.SENSOR_DELAY_UI);
        shakeSensor.setOnShakeListener(new ShakeSensor.OnShakeListener() {
            @Override
            public void onShake(int count) throws java.lang.InstantiationException, IllegalAccessException {
                if (count > 2) {
                    Log.i("Sensor", "Device was shaked");
                    ((MainActivity) getActivity()).playShuffle();
                    memory.shuffleCards();
                    buildGrid();
                }
            }
        });


    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeSensor);
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeSensor, accelerometer, SensorManager.SENSOR_DELAY_UI);
        Toast.makeText(getContext(),"Shake the phone to shuffle the deck.",Toast.LENGTH_SHORT).show();
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
        if (view != null) {
            imageManager = new ImageManager(view.getContext().getAssets());
            memoryGrid = view.findViewById(R.id.memoryGrid);
            buildGrid();
        }
    }

    private void buildGrid() {
        memoryGrid.removeAllViewsInLayout();
        int index = 0;
        for (int row = 0; row < colSize && index < colSize * rowSize; row++) {
            for (int col = 0; col < rowSize && index < colSize * rowSize; col++) {
                final CardView cardView = new CardView(memoryGrid.getContext(), memory.getCard(index++), imageManager);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) cardWidth;
                params.height = (int) cardHeight;
                params.columnSpec = GridLayout.spec(col);
                params.rowSpec = GridLayout.spec(row);
                cardView.setLayoutParams(params);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CardView cardView = (CardView) v;
                        selectCard(cardView);
                    }
                });

                memoryGrid.addView(cardView);

            }
        }

        //((ViewPager)memoryGrid.getParent()).setPageMargin(1);
    }

    private void selectCard(final CardView cardView) {
        final Card card = cardView.getCard();
        if (cardView != guessCard && !card.isPaired()) {
            cardView.showCard();
            if (guessCard == null) {
                guessCard = cardView;
            } else {
                if (card.compareTo(guessCard.getCard()) == 0) {
                    card.setPaired(true);
                    guessCard.getCard().setPaired(true);
                    ((MainActivity) getActivity()).updateStat(dataBaseID);
                    ((MainActivity) getActivity()).playWin();
                } else {
                    final CardView flipCard = guessCard;
                    Runnable hideCards = new Runnable() {
                        @Override
                        public void run() {
                            flipCard.hideCard();
                            cardView.hideCard();
                        }
                    };
                    cardHandler.postDelayed(hideCards, 1000);
                }
                guessCard = null;
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntArray("orderState", memory.saveOrderState());
        outState.putBooleanArray("pairState", memory.savePairState());
    }
}
