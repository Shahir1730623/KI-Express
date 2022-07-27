package com.example.kiexpress.IntroductoryScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.kiexpress.R;
import com.google.android.material.button.MaterialButton;

public class OnBoardingActivity2 extends AppCompatActivity {

    private MaterialButton materialButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_on_boarding2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        materialButton = findViewById(R.id.Btn2);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity2.this, OnBoardingActivity3.class);
                startActivity(intent);
                finish();
            }
        });

    }

}