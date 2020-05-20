package au.edu.jcu.cp3406.brainbusters;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.google.android.material.tabs.TabLayout;

import au.edu.jcu.cp3406.brainbusters.fragments.MemoryFragment;
import au.edu.jcu.cp3406.brainbusters.fragments.MinesweeperFragment;
import au.edu.jcu.cp3406.brainbusters.fragments.SodukuFragment;

public class MainActivity extends AppCompatActivity {

    private StatsDatabaseHelper statsDatabaseHelper;
    AudioManager audioManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = new AudioManager(this);
        statsDatabaseHelper = new StatsDatabaseHelper(this);

        GamePagerAdapter gamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager pager = findViewById(R.id.gamePager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(gamePagerAdapter);
        pager.setPageTransformer(true, new CubeOutTransformer());

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    public void showStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void updateStat(long id) {
        statsDatabaseHelper.insertStat(id);
    }

    public void playWin() {
        audioManager.playSound(R.raw.win);
    }

    public void playShuffle() {
        audioManager.playSound(R.raw.shuffle);
    }

    public void playExplode() {
        audioManager.playSound(R.raw.explode);
    }
}

class GamePagerAdapter extends FragmentPagerAdapter {


    GamePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Soduku";
            case 1:
                return "Minesweeper";
            case 2:
                return "Memory";
        }
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new SodukuFragment();
            case 1:
                return new MinesweeperFragment();
            case 2:
                return new MemoryFragment();
        }
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
