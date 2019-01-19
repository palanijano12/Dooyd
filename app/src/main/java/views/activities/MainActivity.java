package views.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.android.dooyd.R;
import com.google.android.material.tabs.TabLayout;
import views.adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private ViewPager mainViewPager;
    private TabLayout mainTabs;
    private TypedArray tab_Icons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        tab_Icons = getResources().obtainTypedArray(R.array.tab_icons);

        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.tab_titles)));
        mainViewPager.addOnPageChangeListener(this);
        mainTabs.setupWithViewPager(mainViewPager);

        setUpTabs(mainTabs);
    }

    private void initViews() {
        mainViewPager = findViewById(R.id.mainViewPager);
        mainTabs = findViewById(R.id.mainTabs);

    }

    private void setUpTabs(TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(tab_Icons.getResourceId(i, -1));
            }
        }
        tab_Icons.recycle();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == (mainTabs.getTabCount() - 1)) {
            finish();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
