package com.example.kiexpress.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kiexpress.ModelClasses.User;
import com.example.kiexpress.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Registration extends AppCompatActivity {

    TextInputLayout regNameTextField, regEmailTextField, regPhoneTextField, regPasswordTextField;
    Button registerBtn, backToLoginPage;
    DatabaseReference reference;
    long totalUser = 0;
//    private static int REG_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        regNameTextField = findViewById(R.id.regNameTextField);
        regEmailTextField = findViewById(R.id.regEmailTextField);
        regPhoneTextField = findViewById(R.id.regPhoneTextField);
        regPasswordTextField = findViewById(R.id.regPasswordTextField);
        registerBtn = findViewById(R.id.registerBtn);
        backToLoginPage = findViewById(R.id.backToLoginPage);

        reference = FirebaseDatabase.getInstance().getReference("Users");

    }

    private Boolean validateName() {
        String val = regNameTextField.getEditText().getText().toString();
        if (val.isEmpty()) {
            regNameTextField.setError("The field is empty");
            return false;
        } else {
            regNameTextField.setError(null);
            regNameTextField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail() {
        String val = regEmailTextField.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmailTextField.setError("The field is empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmailTextField.setError("Invalid Email Address");
            return false;
        } else {
            regEmailTextField.setError(null);
            regEmailTextField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePhone() {
        String val = regPhoneTextField.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneTextField.setError("The field is empty");
            return false;
        } else if (val.length() != 11) {
            regPhoneTextField.setError("Wrong phone number");
            return false;
        } else {
            regPhoneTextField.setError(null);
            regPhoneTextField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = regPasswordTextField.getEditText().getText().toString();
        String passwordVal = "^" +    // start-of-string
                "(?=.*[0-9])" +       // a digit must occur at least once
                "(?=.*[a-z])" +      // a lower case letter must occur at least once
                "(?=.*[A-Z])" +      // an upper case letter must occur at least once
                "(?=\\S+$)" +      // no whitespace allowed in the entire string
                ".{4,}" +     // anything, at least six places though
                "$";                 // end-of-string

        if (val.isEmpty()) {
            regPasswordTextField.setError("The field is empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPasswordTextField.setError("Password too weak");
            return false;
        } else {
            regPasswordTextField.setError(null);
            regPasswordTextField.setErrorEnabled(false);
            return true;
        }

    }

    public void registerBtnOnClick(View view) {
        if (!validateName() || !validateEmail() || !validatePhone() || !validatePassword()) {
            return;
        }

        String name = regNameTextField.getEditText().getText().toString();
        String email = regEmailTextField.getEditText().getText().toString();
        String phone = "+88" + regPhoneTextField.getEditText().getText().toString();
        String password = regPasswordTextField.getEditText().getText().toString();
        String position = "Customer";

        Intent intent = new Intent(this, VerifyOTP.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    public void backToLoginPage(View view) {
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void backBtn(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}