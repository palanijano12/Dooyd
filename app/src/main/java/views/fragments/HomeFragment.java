package views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.android.dooyd.R;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(rootView);

        List<String> categoryList = new ArrayList<>();
        categoryList.add("PROCUREMENTS");
        categoryList.add("DESIGN & ENGINEERING");
        categoryList.add("CONSTRUCTION");
        categoryList.add("RENTAL EQUIPMENTS");

        getSlideImages();

        homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), categoryList, this);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void onItemClick(int position) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance(position)).addToBackStack(null).commit();
        }

    }

    private void getSlideImages() {

        Call<List<SlideItem>> call = WebService.createApiService().getSlideImages();


        call.enqueue(new Callback<List<SlideItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<SlideItem>> call, @NonNull Response<List<SlideItem>> response) {
                //mainLiveItems.setValue(response.body());
                sliderPagerAdapter = new SliderPagerAdapter(getActivity(), response.body());
                slideViewPager.setAdapter(sliderPagerAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<SlideItem>> call, @NonNull Throwable t) {

            }
        });
    }
}
