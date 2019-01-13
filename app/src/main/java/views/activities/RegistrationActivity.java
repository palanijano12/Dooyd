package views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import com.android.dooyd.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editTextName;
    private TextInputEditText editTextMobile;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;

    private MaterialButton buttonRegister;

    private AppCompatTextView loginNow;

    private String _name, _mobile, _email, _password;

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
        buttonRegister = findViewById(R.id.registerButton);
        loginNow = findViewById(R.id.login_now_view);

        //SET CLICK EVENTS
        buttonRegister.setOnClickListener(this);
        loginNow.setOnClickListener(this);
    }


    private void register() {
        if (editTextName.getText() != null) {
            _name = editTextName.getText().toString().trim();
        } else {
            showToast("Name not be empty.");
        }
        if (editTextMobile.getText() != null) {
            _mobile = editTextMobile.getText().toString().trim();
        } else {
            showToast("Mobile number not be empty");
        }
        if (editTextEmail.getText() != null) {
            _email = editTextEmail.getText().toString().trim();
        } else {
            showToast("Email not be empty");
        }
        if (editTextPassword.getText() != null) {
            _password = editTextPassword.getText().toString().trim();


        } else {
            showToast("Password not be empty");
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
                break;
            }
        }

    }
}
