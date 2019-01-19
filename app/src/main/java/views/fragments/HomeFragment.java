package views.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.android.dooyd.R;
import datamodel.Constants;
import datamodel.SlideItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import views.adapter.HomeRecyclerAdapter;
import views.adapter.SliderPagerAdapter;
import views.listener.HomeRecyclerListener;
import webservices.WebService;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeRecyclerListener {

    private RecyclerView homeRecyclerView;
    private HomeRecyclerAdapter homeRecyclerAdapter;

    private SliderPagerAdapter sliderPagerAdapter;
    private ViewPager slideViewPager;

    private int oldDragPosition = 0;
    private Animator pagerAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(rootView);
        if (getActivity() != null) {
            getActivity().setTitle(getString(R.string.app_name));
        }

        List<String> categoryList = new ArrayList<>();
        categoryList.add("PROCUREMENTS");
        categoryList.add("DESIGN & ENGINEERING");
        categoryList.add("CONSTRUCTION");
        categoryList.add("RENTAL EQUIPMENTS");

        getSlideImages();

        homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), categoryList, this);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeRecyclerView.setHasFixedSize(true);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeRecyclerView.setAdapter(homeRecyclerAdapter);
    }

    private void initViews(View v) {

        slideViewPager = v.findViewById(R.id.slideViewPager);
        homeRecyclerView = v.findViewById(R.id.homeRecyclerView);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onItemClick(int position, String categoryName) {
        if (getActivity() != null) {

            Bundle arguments = new Bundle();
            arguments.putInt("POSITION", position);
            arguments.putString("CATEGORY_NAME", categoryName);

            Fragment fragment = new MainFragment();
            fragment.setArguments(arguments);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }

    }

    private void getSlideImages() {

        Call<List<SlideItem>> call = WebService.createApiService().getSlideImages();


        call.enqueue(new Callback<List<SlideItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<SlideItem>> call, @NonNull Response<List<SlideItem>> response) {
                sliderPagerAdapter = new SliderPagerAdapter(getActivity(), response.body());
                slideViewPager.setAdapter(sliderPagerAdapter);

                if (slideViewPager.getAdapter() != null) {
                    slideViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animatePagerTransition();
                        }
                    }, Long.parseLong(Constants.SLIDER_ANIMATION_DURATION));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<SlideItem>> call, @NonNull Throwable t) {

            }
        });
    }


    private void animatePagerTransition() {
        if (pagerAnimation != null) {
            pagerAnimation.cancel();
        }
        pagerAnimation = getPagerTransitionAnimation();
        if (slideViewPager.beginFakeDrag()) {
            pagerAnimation.start();
        }
    }


    private Animator getPagerTransitionAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, (slideViewPager.getMeasuredWidth() - 1));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                slideViewPager.endFakeDrag();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                slideViewPager.endFakeDrag();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                slideViewPager.endFakeDrag();
                oldDragPosition = 0;
                slideViewPager.beginFakeDrag();
            }
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int dragPosition = (Integer) animation.getAnimatedValue();
                int dragOffset = dragPosition - oldDragPosition;
                oldDragPosition = dragPosition;
                slideViewPager.fakeDragBy(-dragOffset);
            }
        });

        animator.setDuration(Long.parseLong(Constants.SLIDER_ANIMATION_DURATION));
        animator.setRepeatCount(2);

        return animator;
    }


}
