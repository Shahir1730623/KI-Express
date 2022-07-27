package com.example.kiexpress.Users.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.kiexpress.R;

public class FAQ extends AppCompatActivity {

    String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        userEmail = getIntent().getStringExtra("email");
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(FAQ.this, CustomerDashboard.class);
        intent.putExtra("email", userEmail);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}