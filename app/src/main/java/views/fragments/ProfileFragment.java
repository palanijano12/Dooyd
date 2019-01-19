package views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import com.android.dooyd.R;
import com.google.android.material.button.MaterialButton;
import datamodel.Constants;
import datamodel.ProfileItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webservices.WebService;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private AppCompatEditText profileNameView;
    private AppCompatEditText profileMobileView;
    private AppCompatEditText profileEmailView;
    private AppCompatEditText profilePasswordView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(rootView);
        if (getActivity() != null) {
            getActivity().setTitle("Profile");
        }

        getProfileData();
        return rootView;
    }

    private void initViews(View v) {

        profileNameView = v.findViewById(R.id.profileNameView);
        profileMobileView = v.findViewById(R.id.profileMobileView);
        profileEmailView = v.findViewById(R.id.profileEmailView);
        profilePasswordView = v.findViewById(R.id.profilePasswordView);

        MaterialButton updateButton = v.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);

    }

    private void updateProfileData() {
    }

    private void getProfileData() {

        WebService.createApiService().getCustomerProfile("Bearer " + Constants.USER_TOKEN).enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(@NonNull Call<ProfileItem> call, @NonNull Response<ProfileItem> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        profileNameView.setText(response.body().getCustomerName());
                        profileMobileView.setText(response.body().getCustomerMobile());
                        profileEmailView.setText(response.body().getCustomerEmail());
                        profilePasswordView.setText("");
                        if (!TextUtils.isEmpty(profileNameView.getText()) && profileNameView.getText() != null) {
                            profileNameView.setSelection(profileNameView.getText().length());
                        }

                    } else {
                        showToast("Error retrieving profile data.");
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileItem> call, @NonNull Throwable t) {
                showToast("" + t.getMessage());
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton: {
                updateProfileData();
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
