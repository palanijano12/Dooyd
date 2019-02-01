package views.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.dooyd.R;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import datamodel.Constants;
import datamodel.DescItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import views.activities.LoginActivity;
import views.activities.ProductActivity;
import webservices.WebService;

public class ProductDescriptionFragment extends Fragment implements LifecycleOwner, View.OnClickListener {

    private LifecycleRegistry mLifecycleRegistry;
    private String productId;

    private AppCompatTextView descProductTitle;
    private AppCompatTextView descProductPrice;
    private AppCompatTextView descProductCutPrice;
    private AppCompatTextView descProductAvail;
    private AppCompatTextView descProductCategory;
    private AppCompatTextView descProductDesc;

    private AppCompatImageView descProductImageView;

    private ProgressBar descProgressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String apiUrl = "Product/GetProductDetail/";

        View rootView = inflater.inflate(R.layout.fragment_product_description, container, false);
        initViews(rootView);


        if (getActivity() != null) {

            getActivity().setTitle("");

            ActionBar actionBar = ((ProductActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(true);
            }
            if (getArguments() != null) {
                productId = getArguments().getString(Constants.KEY_PRODUCT_ID, "");
            }
            // position = getArguments().getInt("POSITION", 0);
        }


        mLifecycleRegistry = new LifecycleRegistry(this);

        loadProductDescription(apiUrl + productId);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);


    }

    private void loadProductDescription(String url) {

        Call<DescItem> call = WebService.createApiService().getProductDescription(url);

        call.enqueue(new Callback<DescItem>() {
            @Override
            public void onResponse(@NonNull Call<DescItem> call, @NonNull Response<DescItem> response) {

                if (response.body() != null) {
                    if (getActivity() != null) {
                        getActivity().setTitle(response.body().getName());
                    }
                    descProductTitle.setText(response.body().getName());
                    descProductPrice.append("" + response.body().getPrice());
                    descProductCutPrice.append("" + response.body().getCutPrice());
                    if (response.body().getIsAvailable()) {
                        descProductAvail.setText(Constants.IN_STOCK);
                        descProductAvail.setTextColor(Color.parseColor("#005313"));
                    } else {
                        descProductAvail.setText(Constants.OUT_OF_STOCK);
                        descProductAvail.setTextColor(Color.RED);
                    }
                    descProductCategory.setText(response.body().getCategory());
                    descProductDesc.setText(response.body().getShortDescription());

                    if (getActivity() != null) {
                        Glide.with(getActivity().getApplicationContext()).load(response.body().getProductImage().get(0).getFileUrl()).into(descProductImageView);

                    }


                    descProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(@NonNull Call<DescItem> call, @NonNull Throwable t) {

            }
        });
    }


    private void initViews(View v) {

        descProductImageView = v.findViewById(R.id.descProductImageView);
        descProductTitle = v.findViewById(R.id.descProductTitleView);
        descProductPrice = v.findViewById(R.id.descProductPriceView);
        descProductCutPrice = v.findViewById(R.id.descProductCutPriceView);
        descProductAvail = v.findViewById(R.id.descProductAvailView);
        descProductCategory = v.findViewById(R.id.descProductCategoryView);
        descProductDesc = v.findViewById(R.id.descProductCategoryDescView);
        descProgressBar = v.findViewById(R.id.productDescLoader);

        MaterialButton btnAddToCart = v.findViewById(R.id.descAddCartButton);
        //MaterialButton btnAddWhislist = v.findViewById(R.id.descAddWhislistButton);
        MaterialButton btnChatVendor = v.findViewById(R.id.descChatVendorButton);
        btnAddToCart.setOnClickListener(this);
        // btnAddWhislist.setOnClickListener(this);
        btnChatVendor.setOnClickListener(this);

        descProductCutPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }
}