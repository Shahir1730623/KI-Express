package com.example.kiexpress.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.kiexpress.LoginSignUp.Login;
import com.example.kiexpress.R;
import com.example.kiexpress.Users.Customer.CustomerDashboard;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private static final String SHARED_PREFS_NAME="mypref";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME,MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                if(hasLoggedIn){
//                    Intent intent = new Intent(MainActivity.this,CustomerDashboard.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else{
//                    Intent intent = new Intent(MainActivity.this,Login.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//
//            }
//        },SPLASH_TIME_OUT);


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity.this, CustomerDashboard.class);;
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }


}