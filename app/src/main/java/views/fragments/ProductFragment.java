package views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.dooyd.R;
import datamodel.Constants;
import datamodel.MainItem;
import viewmodel.MainViewModel;
import views.activities.ProductActivity;
import views.adapter.ProductRecyclerAdapter;
import views.listener.ProductRecyclerListener;

import java.util.List;

public class ProductFragment extends Fragment implements LifecycleOwner, ProductRecyclerListener {


    private RecyclerView mainRecyclerView;
    private ProductRecyclerAdapter mainRecyclerAdapter;
    private LifecycleRegistry mLifecycleRegistry;
    private ProgressBar productProgress;


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

                ActionBar actionBar = ((ProductActivity) getActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayShowTitleEnabled(true);
                }


                getActivity().setTitle("" + getArguments().getString(Constants.KEY_CATEGORY_NAME, ""));
            }
            itemPosition = getArguments().getInt(Constants.KEY_POSITION, 0);
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

                if (getActivity() != null) {
                    mainRecyclerAdapter = new ProductRecyclerAdapter(getActivity().getApplicationContext(), mainItems, ProductFragment.this, itemPosition);
                    mainRecyclerAdapter.setHasStableIds(true);
                    mainRecyclerView.setAdapter(mainRecyclerAdapter);
                    productProgress.setVisibility(View.GONE);
                }

            }
        });

    }


    private void initViews(View v) {
        mainRecyclerView = v.findViewById(R.id.mainRecyclerView_);
        productProgress = v.findViewById(R.id.productLoader);
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

    }

    @Override
    public void onItemClick(int position, String productId, int viewId) {

        if (getActivity() != null) {

            switch (viewId) {
                case R.id.productCard: {
                    Bundle arguments = new Bundle();
                    arguments.putInt(Constants.KEY_POSITION, position);
                    arguments.putString(Constants.KEY_PRODUCT_ID, productId);

                    Fragment fragment = new ProductDescriptionFragment();
                    fragment.setArguments(arguments);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.product_frame_container, fragment).addToBackStack(null).commit();

                    break;
                }

                case R.id.item_add_cart: {
                    addItemToCart();
                    break;
                }
            }


        }

    }


    private void addItemToCart() {

    }

}
