package views.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import views.fragments.CartFragment;
import views.fragments.HomeFragment;
import views.fragments.ProfileFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles;

    public MainPagerAdapter(@NonNull FragmentManager fm, String[] tabTitles) {
        super(fm);
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return HomeFragment.newInstance();
            }
            case 1: {
                return new CartFragment();
            }
            case 2: {
                return new CartFragment();
            }
            case 3: {
                return new ProfileFragment();
            }
            default: {
                return new CartFragment();
            }
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 5;
    }
}
