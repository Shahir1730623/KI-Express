package com.example.kiexpress.Users.Employee;

import static com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType.SLIDE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kiexpress.LoginSignUp.Login;
import com.example.kiexpress.R;
import com.example.kiexpress.Users.Common.StaffProfile;
import com.example.kiexpress.Users.Common.NewOrders;
import com.example.kiexpress.Users.Common.ViewOrder;
import com.example.kiexpress.recyclerview.SliderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class EmployeeDashboard extends AppCompatActivity {

    String nameFromDB = "", emailFromDB = "", phoneFromDB = "", passwordFromDB = "", positionFromDB = "";
    SliderView sliderView;
    int[] images = {
            R.drawable.img2,
            R.drawable.img2,
            R.drawable.img3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        //SliderView
        sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);

        //Animations(SliderView)
        sliderView.setIndicatorAnimation(SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        showUserData();
    }

    private void showUserData() {
        phoneFromDB = getIntent().getStringExtra("phone");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(phoneFromDB);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        nameFromDB = snapshot1.child("name").getValue().toString();
                        emailFromDB = snapshot1.child("email").getValue().toString();
                        phoneFromDB = snapshot1.child("phone").getValue().toString();
                        passwordFromDB = snapshot1.child("password").getValue().toString();
                        positionFromDB = snapshot1.child("position").getValue().toString();
                    }
                } else
                    Toast.makeText(EmployeeDashboard.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeDashboard.this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to Log out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity(); // clear the current activity and all the activities in the stack
                startActivity(new Intent(EmployeeDashboard.this, Login.class));
                Toast.makeText(EmployeeDashboard.this, "Succesfully Logged out!", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public void ordersBtn(View view) {
        Intent intent = new Intent(EmployeeDashboard.this, ViewOrder.class);
        intent.putExtra("phone", phoneFromDB);
        startActivity(intent);
    }

    public void profileBtn(View view) {
        Intent intent = new Intent(EmployeeDashboard.this, StaffProfile.class);
        intent.putExtra("name", nameFromDB);
        intent.putExtra("email", emailFromDB);
        intent.putExtra("phone", phoneFromDB);
        intent.putExtra("password", passwordFromDB);
        intent.putExtra("position", positionFromDB);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}