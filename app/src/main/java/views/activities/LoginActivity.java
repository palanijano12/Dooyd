package views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.dooyd.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editTextUserName;
    private TextInputEditText editTextPassword;
    private MaterialButton loginButton;
    private AppCompatTextView registerNow;
    private AppCompatTextView forgotPassword;

    private String userName, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

    }

    private void initViews() {
        editTextUserName = findViewById(R.id.loginUserNameView);
        editTextPassword = findViewById(R.id.loginPasswordView);
        loginButton = findViewById(R.id.loginButton);
        registerNow = findViewById(R.id.registerNow);
        forgotPassword = findViewById(R.id.forgot_password);

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

        if (editTextUserName.getText() != null) {
            userName = editTextUserName.getText().toString().trim();
        } else {
            showToast("Mobile/Email not be empty");
        }

        if (editTextPassword.getText() != null) {
            password = editTextPassword.getText().toString().trim();
        } else {
            showToast("Password not be empty");
        }


    }
}
