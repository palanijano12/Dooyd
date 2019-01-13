package views.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import views.fragments.CartFragment;
import views.fragments.HomeFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] tabTitles = new String[]{"Home", "Cart", "Order", "Profile", "Exit"};


    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
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
                return new CartFragment();
            }
            case 4: {
                return new CartFragment();
            }
            case 5: {
                return new CartFragment();
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
