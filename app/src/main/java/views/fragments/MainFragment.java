package views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.dooyd.R;
import datamodel.MainItem;
import viewmodel.MainViewModel;
import views.adapter.MainRecyclerAdapter;

import java.util.List;

public class MainFragment extends Fragment implements LifecycleOwner {


    private RecyclerView mainRecyclerView;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LifecycleRegistry mLifecycleRegistry;

    private int itemPosition;
    private final String[] apiUrls =
            {
                    "Product/GetProcurementProducts/1",
                    "Product/GetDesignEngineeringProducts//1",
                    "Product/GetConstructionProducts/1",
                    "Product/GetRentalProducts/1"
            };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(rootView);

        if (getArguments() != null) {
            if (getActivity() != null) {
                getActivity().setTitle("" + getArguments().getString("CATEGORY_NAME", ""));
            }
            itemPosition = getArguments().getInt("POSITION", 0);
        }


        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        mLifecycleRegistry = new LifecycleRegistry(this);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getMainItems(apiUrls[itemPosition]).observe(this, new Observer<List<MainItem>>() {
            @Override
            public void onChanged(List<MainItem> mainItems) {
                mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), mainItems);
                mainRecyclerAdapter.setHasStableIds(true);
                mainRecyclerView.setAdapter(mainRecyclerAdapter);
            }
        });

    }


    private void initViews(View v) {
        mainRecyclerView = v.findViewById(R.id.mainRecyclerView);
    }


    @Override
    @NonNull
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        if (getActivity() != null) {
            getActivity().setTitle(getString(R.string.app_name));
        }
    }
}
