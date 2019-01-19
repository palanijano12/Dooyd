package views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.dooyd.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webservices.WebService;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editTextName;
    private TextInputEditText editTextMobile;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

    }

    private void initViews() {
        editTextName = findViewById(R.id.regNameView);
        editTextMobile = findViewById(R.id.regMobileView);
        editTextEmail = findViewById(R.id.regEmailView);
        editTextPassword = findViewById(R.id.regPasswordView);
        MaterialButton buttonRegister = findViewById(R.id.registerButton);
        AppCompatTextView loginNow = findViewById(R.id.login_now_view);

        //SET CLICK EVENTS
        buttonRegister.setOnClickListener(this);
        loginNow.setOnClickListener(this);
    }


    private void register() {

        JSONObject rawJson = new JSONObject();

        if (!TextUtils.isEmpty(editTextName.getText()) && editTextName.getText() != null) {

            if (!TextUtils.isEmpty(editTextMobile.getText()) && editTextMobile.getText() != null) {

                if (!TextUtils.isEmpty(editTextEmail.getText()) && editTextEmail.getText() != null) {

                    if (!TextUtils.isEmpty(editTextPassword.getText()) && editTextPassword.getText() != null) {

                        try {
                            rawJson.put("name", editTextName.getText().toString());
                            rawJson.put("mobile", editTextMobile.getText().toString());
                            rawJson.put("email", editTextEmail.getText().toString());
                            rawJson.put("password", editTextPassword.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), rawJson.toString());

                        WebService.createApiService().addCustomerProfile(body).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                                Log.d("REG RESPONSE", "" + response);
                                if (response.body() != null && response.isSuccessful() && response.message().matches("OK")) {
                                    if (response.body().matches("Registered successfully & Activation Mail sent to your mail")) {
                                        showToast(response.body());
                                        editTextName.getText().clear();
                                        editTextMobile.getText().clear();
                                        editTextEmail.getText().clear();
                                        editTextPassword.getText().clear();
                                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                        finish();
                                    } else {
                                        if (response.body().matches("Mobile/Email alredy exists")) {
                                            showToast(response.body());
                                            editTextName.requestFocus();
                                        } else {
                                            showToast("Error occurred try again later.");
                                        }
                                    }

                                } else {
                                    showToast("Error occurred try again later.");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                showToast("" + t.getMessage());
                            }
                        });

                    } else {
                        showToast("Password not be empty");
                    }

                } else {
                    showToast("Email not be empty");
                }
            } else {
                showToast("Mobile number not be empty");
            }
        } else {
            showToast("Name not be empty.");
        }


    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.registerButton: {
                register();
                break;
            }

            case R.id.login_now_view: {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
                break;
            }
        }

    }
}
