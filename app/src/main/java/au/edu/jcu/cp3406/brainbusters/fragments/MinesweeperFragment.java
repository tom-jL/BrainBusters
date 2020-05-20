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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import au.edu.jcu.cp3406.brainbusters.ImageManager;
import au.edu.jcu.cp3406.brainbusters.MainActivity;
import au.edu.jcu.cp3406.brainbusters.R;
import au.edu.jcu.cp3406.brainbusters.ShakeSensor;
import au.edu.jcu.cp3406.brainbusters.models.Minesweeper;
import au.edu.jcu.cp3406.brainbusters.models.Soduku;
import au.edu.jcu.cp3406.brainbusters.views.MineView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MinesweeperFragment extends Fragment {

    private Minesweeper minesweeper;
    private ImageManager imageManager;
    private GridLayout mineGrid;
    private float mineViewWidth;

    Runnable restart;
    Handler handler;


    private long dataBaseID = 1;

    public MinesweeperFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        restart = new Runnable() {
            @Override
            public void run() {
                minesweeper = new Minesweeper();
                buildGrid();
            }
        };
        minesweeper = new Minesweeper();
        if (savedInstanceState != null) {
            minesweeper.loadState(savedInstanceState.getIntArray("state"));
        }


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = (float) displayMetrics.widthPixels;
        float screenHeight = (float) displayMetrics.heightPixels;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mineViewWidth = (float) (screenHeight * 0.8 / 8);
        } else {
            mineViewWidth = screenWidth / 8;
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minesweeper, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            imageManager = new ImageManager(view.getContext().getAssets());
            mineGrid = view.findViewById(R.id.mineGrid);
            buildGrid();
        }
    }

    private void buildGrid() {
        mineGrid.removeAllViewsInLayout();
        int rowCount = 0;
        int colCount = 0;
        for (int[] row : minesweeper.getGrid()) {
            for (final int col : row) {
                //final CardView cardView = new CardView(mineGrid.getContext(), minesweeper.getCard(index), imageManager);
                MineView mineView = new MineView(mineGrid.getContext(), imageManager);
                mineView.setImageBitmap(imageManager.getBlankMine());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) mineViewWidth;
                params.height = (int) mineViewWidth;
                params.columnSpec = GridLayout.spec(colCount);
                params.rowSpec = GridLayout.spec(rowCount);
                mineView.setLayoutParams(params);

                final int finalColCount = colCount;
                final int finalRowCount = rowCount;
                mineView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View mineView) {
                        switch (col) {
                            case 9:
                                ((MainActivity) getActivity()).playExplode();
                                revealAll();
                                handler.postDelayed(restart, 3000);
                                break;
                            case 0:
                                revealBlock(finalRowCount, finalColCount);
                                break;
                            default:
                                ((MineView) mineView).revealMine(col);
                        }
                        if (sweptMines()) {
                            ((MainActivity) getActivity()).updateStat(dataBaseID);
                            ((MainActivity) getActivity()).playWin();
                            handler.postDelayed(restart, 3000);
                        }
                    }
                });

                mineGrid.addView(mineView);
                colCount++;
            }
            colCount = 0;
            rowCount++;
        }

        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            float screenWidth = (float) displayMetrics.widthPixels;
            ((ViewPager) mineGrid.getParent()).setPageMargin((int) (screenWidth / 2 - (mineViewWidth * 9) / 2));
        }
    }

    private boolean sweptMines() {
        int count = 0;
        for(int index = 0; index < mineGrid.getChildCount(); index++){
            if(((MineView) mineGrid.getChildAt(index)).isRevealed()){
                count++;
            }
        }
        return count == (64 - (minesweeper.getDifficulty().ordinal() + 1) * 8);
    }

    private void revealAll() {
        int index = 0;
        for (int row = 0; row < minesweeper.getGrid().length; row++) {
            for (int col = 0; col < minesweeper.getGrid()[row].length; col++) {
                ImageView mineView = (ImageView) mineGrid.getChildAt(index);
                mineView.setImageBitmap(imageManager.getMineImage(minesweeper.getGrid()[row][col]));
                index++;
            }
        }
    }

    private void revealBlock(int row, int col) {

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                try {
                    MineView mineView = (MineView) mineGrid.getChildAt((row + x) * 8 + (col + y));
                    if (minesweeper.getGrid()[row + x][col + y] == 0 && !(x == 0 && y == 0) && !mineView.isRevealed()) {
                        revealBlock(row + x, col + y);
                    } else {
                        mineView.revealMine(minesweeper.getGrid()[row + x][col + y]);
                    }
                    mineView = (MineView) mineGrid.getChildAt((row) * 8 + (col));
                    mineView.revealMine(minesweeper.getGrid()[row][col]);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putIntArray("state", minesweeper.saveState());
    }


}
