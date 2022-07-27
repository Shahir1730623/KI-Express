package com.example.kiexpress.IntroductoryScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.kiexpress.IntroductoryScreens.OnBoardingActivity1;
import com.example.kiexpress.LoginSignUp.StartUpScreen;
import com.example.kiexpress.R;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, logo_img;
    Animation topanim,bottomanim;
    private static int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        logo_img = findViewById(R.id.logo_img);

        logo.setAnimation(topanim);
        logo_img.setAnimation(bottomanim);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(IntroductoryActivity.this, StartUpScreen.class);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN);

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}

