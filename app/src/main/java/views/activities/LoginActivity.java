package views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.dooyd.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import datamodel.Constants;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webservices.WebService;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editTextUserName;
    private TextInputEditText editTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

    }

    private void initViews() {
        editTextUserName = findViewById(R.id.loginUserNameView);
        editTextPassword = findViewById(R.id.loginPasswordView);
        MaterialButton loginButton = findViewById(R.id.loginButton);
        AppCompatTextView registerNow = findViewById(R.id.registerNow);
        AppCompatTextView forgotPassword = findViewById(R.id.forgot_password);

        //SET CLICK EVENTS
        loginButton.setOnClickListener(this);
        registerNow.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton: {
                login();
                break;
            }

            case R.id.registerNow: {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
                break;
            }

            case R.id.forgot_password: {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void login() {

        JSONObject rawJson = new JSONObject();

        if (!TextUtils.isEmpty(editTextUserName.getText()) && editTextUserName.getText() != null) {
            String userName = editTextUserName.getText().toString();

            if (!TextUtils.isEmpty(editTextPassword.getText()) && editTextPassword.getText() != null) {
                String password = editTextPassword.getText().toString();

                try {
                    rawJson.put("username", userName);
                    rawJson.put("password", password);
                    rawJson.put("userType", "1");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), rawJson.toString());

                WebService.createApiService().validateCustomerLogin(body).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                        Log.d("REG RESPONSE", "" + response);

                        if (response.isSuccessful() && response.message().matches("OK")) {
                            //SUCCESSFUL LOGIN
                            try {
                                JSONObject responseJson = new JSONObject(response.body());
                                Constants.USER_TOKEN = responseJson.getString("token");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {

                            if (response.message().matches("Bad Request")) {
                                try {
                                    if (response.errorBody() != null) {
                                        showToast("" + response.errorBody().string());
                                    } else {
                                        showToast("Error occurred try again later.");
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                showToast("Error occurred try again later.");
                            }
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

            showToast("Mobile/Email not be empty");
        }


    }
}
