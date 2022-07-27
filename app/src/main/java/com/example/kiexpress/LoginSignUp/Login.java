package com.example.kiexpress.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kiexpress.Users.Admin.AdminDashboard;
import com.example.kiexpress.Users.Admin.AdminViewUsers;
import com.example.kiexpress.Users.Customer.CustomerDashboard;
import com.example.kiexpress.R;
import com.example.kiexpress.Users.Employee.EmployeeDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    ImageView logo_img;
    TextView logo_welcomeTxt, logo_slogan;
    TextInputLayout emailTextField, passwordTextField;
    AppCompatButton loginBtn;
    Button callRegisterPageBtn;
    ProgressBar progress_bar;
    ImageView back_Btn;


    private static final String SHARED_PREFS_NAME="mypref";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize
        logo_img = findViewById(R.id.logo_img);
        logo_welcomeTxt = findViewById(R.id.logo_welcomeTxt);
        logo_slogan = findViewById(R.id.logo_slogan);
        emailTextField = findViewById(R.id.emailTextField);
        passwordTextField = findViewById(R.id.passwordTextField);
        loginBtn = findViewById(R.id.loginBtn);
        callRegisterPageBtn = findViewById(R.id.callRegisterPageBtn);
        progress_bar = findViewById(R.id.progress_bar);
        back_Btn = findViewById(R.id.back_Btn);


    }

    private Boolean validateEmail() {
        String val = emailTextField.getEditText().getText().toString();

        if (val.isEmpty()) {
            emailTextField.setError("The field is empty");
            return false;
        }

        else {
            emailTextField.setError(null);
            emailTextField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = passwordTextField.getEditText().getText().toString();

        if (val.isEmpty()) {
            passwordTextField.setError("The field is empty");
            return false;
        }

        else {
            passwordTextField.setError(null);
            passwordTextField.setErrorEnabled(false);
            return true;
        }

    }

    public void goToRegisterPage(View view) {
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
    }

    public void loginBtnOnClick(View view) {
        if (!validateEmail() || !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredEmail = emailTextField.getEditText().getText().toString().trim();
        final String userEnteredPassword = passwordTextField.getEditText().getText().toString().trim();

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(KEY_EMAIL,userEnteredEmail);
//        editor.putString(KEY_PASSWORD,userEnteredPassword);
//        editor.apply();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    emailTextField.setError(null);
                    emailTextField.setErrorEnabled(false);

                    String emailFromDB="",phoneFromDB="",positionFromDB="",passwordFromDB="";
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        passwordFromDB = snapshot1.child("password").getValue().toString();
                    }

                    if (passwordFromDB.equals(userEnteredPassword)) {
                        emailTextField.setError(null);
                        emailTextField.setErrorEnabled(false);
                        progress_bar.setVisibility(View.VISIBLE);

                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            emailFromDB = snapshot1.child("email").getValue().toString();
                            phoneFromDB = snapshot1.child("phone").getValue().toString();
                            positionFromDB = snapshot1.child("position").getValue().toString();
                        }

                        if(positionFromDB.equals("Admin")){
                            Intent intent = new Intent(Login.this, AdminDashboard.class);
                            intent.putExtra("email",emailFromDB);
                            startActivity(intent);
                        }

                        else if(positionFromDB.equals("Employee")){
                            Intent intent = new Intent(Login.this, EmployeeDashboard.class);
                            intent.putExtra("email",emailFromDB);
                            startActivity(intent);
                        }

                        else{
                            Intent intent = new Intent(Login.this, CustomerDashboard.class);
                            intent.putExtra("email",emailFromDB);
                            startActivity(intent);
                        }


                    }

                    else {
                        passwordTextField.setError("Wrong Password");
                        passwordTextField.requestFocus();
                    }
               }

                else{
                    emailTextField.setError("This Email does not exist");
                    emailTextField.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    public void forgetPassword(View view) {
        Intent intent = new Intent(Login.this,ForgetPassword.class);;
        startActivity(intent);
    }

    public void backToStartUpScreen(View view) {
        Intent intent = new Intent(Login.this,StartUpScreen.class);;
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}