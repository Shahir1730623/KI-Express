package com.example.kiexpress.IntroductoryScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.kiexpress.R;
import com.google.android.material.button.MaterialButton;

public class OnBoardingActivity1 extends AppCompatActivity {

    private MaterialButton materialButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_on_boarding1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    public void NextBtnOnClick(View view) {
        Intent intent=new Intent(OnBoardingActivity1.this, OnBoardingActivity2.class);
        startActivity(intent);
        finish();
    }
}