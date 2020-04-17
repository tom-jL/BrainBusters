package au.edu.jcu.cp3406.brainbusters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

import au.edu.jcu.cp3406.brainbusters.models.Minesweeper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GamePagerAdapter gamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager pager = (ViewPager) findViewById(R.id.gamePager);
        pager.setAdapter(gamePagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }
}

class GamePagerAdapter extends FragmentPagerAdapter{

    public GamePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
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


}
