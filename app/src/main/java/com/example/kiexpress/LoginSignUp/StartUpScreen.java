package com.example.kiexpress.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.kiexpress.R;

public class StartUpScreen extends AppCompatActivity {

    Button login_Btn,register_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);

        login_Btn = findViewById(R.id.login_Btn);
        register_Btn = findViewById(R.id.register_Btn);
    }

    public void goToLoginScreen(View view) {
        Intent intent = new Intent(StartUpScreen.this,Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(login_Btn,"login_transition");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void goToRegisterScreen(View view) {
        Intent intent = new Intent(StartUpScreen.this,Registration.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(register_Btn,"register_transition");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}