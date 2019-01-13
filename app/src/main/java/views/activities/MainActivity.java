package views.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.android.dooyd.R;
import com.google.android.material.tabs.TabLayout;
import views.adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {


    private ViewPager mainViewPager;
    private TabLayout mainTabs;

    private final int[] tabIcons = {R.drawable.ic_home_black_24dp, R.drawable.ic_shopping_cart_black_24dp, R.drawable.ic_shopping_basket_black_24dp,
            R.drawable.ic_person_black_24dp, R.drawable.ic_dashboard_black_24dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
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
                tab.setIcon(tabIcons[i]);
            }
        }
    }


}
