package com.example.kiexpress.Users.Customer;

import static com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType.SLIDE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kiexpress.LoginSignUp.Login;
import com.example.kiexpress.R;
import com.example.kiexpress.recyclerview.SliderAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class CustomerDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    SliderView sliderView;
    String nameFromDB = "", emailFromDB = "", phoneFromDB = "", passwordFromDB = "", positionFromDB = "";
    int[] images = {
            R.drawable.img2,
            R.drawable.img2,
            R.drawable.img3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        //Hooks
        menuIcon = findViewById(R.id.menuIcon);
        contentView = findViewById(R.id.contentView);

        //SliderView
        sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);

        //Animations(SliderView)
        sliderView.setIndicatorAnimation(SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        //Method Calls
        showUserData();
        navigationDrawer();


    }

    private void showUserData() {
        emailFromDB = getIntent().getStringExtra("email");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("email").equalTo(emailFromDB);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        nameFromDB = snapshot1.child("name").getValue().toString();
//                        emailFromDB = snapshot1.child("email").getValue().toString();
                        phoneFromDB = snapshot1.child("phone").getValue().toString();
                        passwordFromDB = snapshot1.child("password").getValue().toString();
                        positionFromDB = snapshot1.child("position").getValue().toString();
                    }
                } else
                    Toast.makeText(CustomerDashboard.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void navigationDrawer() {
        navigationView.bringToFront(); //To interract with the navigationView
        navigationView.setNavigationItemSelectedListener(this); //Enables us to click the navigation items

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - 0.7f);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_orders:
                intent = new Intent(CustomerDashboard.this, PastOrders.class);
                intent.putExtra("phone", phoneFromDB);
                startActivity(intent);
                break;

            case R.id.nav_Profile:
                intent = new Intent(CustomerDashboard.this, CustomerProfile.class);
                intent.putExtra("name", nameFromDB);
                intent.putExtra("email", emailFromDB);
                intent.putExtra("phone", phoneFromDB);
                intent.putExtra("password", passwordFromDB);
                intent.putExtra("position", positionFromDB);
                startActivity(intent);
                break;

            case R.id.nav_FAQ:
                intent = new Intent(CustomerDashboard.this, FAQ.class);
                intent.putExtra("email", emailFromDB);
                startActivity(intent);
                break;

            case R.id.nav_Contact_Us:
                intent = new Intent(CustomerDashboard.this, ContactUs.class);
                intent.putExtra("email", emailFromDB);
                startActivity(intent);
                break;

        }

        return true; // Navigation item will be selected
    }


    public void goToCustomerForm(View view) {
        Intent intent = new Intent(CustomerDashboard.this, CustomerForm.class);
        intent.putExtra("email", emailFromDB);
        intent.putExtra("name", nameFromDB);
        intent.putExtra("phone", phoneFromDB);
        startActivity(intent);
    }

    public void ordersBtn(View view) {
        Intent intent = new Intent(CustomerDashboard.this, PastOrders.class);
        intent.putExtra("phone", phoneFromDB);
        startActivity(intent);
    }

    public void notificBtn(View view) {
    }

    public void profileBtn(View view) {
        Intent intent = new Intent(CustomerDashboard.this, CustomerProfile.class);
        intent.putExtra("name", nameFromDB);
        intent.putExtra("email", emailFromDB);
        intent.putExtra("phone", phoneFromDB);
        intent.putExtra("password", passwordFromDB);
        intent.putExtra("position", positionFromDB);
        startActivity(intent);
    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDashboard.this); //Display dialog message
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to Log out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity(); // clear the current activity and all the activities in the stack
                startActivity(new Intent(CustomerDashboard.this, Login.class));
                Toast.makeText(CustomerDashboard.this, "Succesfully Logged out!", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }
}